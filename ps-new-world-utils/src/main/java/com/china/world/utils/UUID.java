package com.china.world.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Peng
 * @date 2021/11/3 9:32
 */
public class UUID implements java.io.Serializable, Comparable<UUID> {


    /**
     * 此UUID的最高64有效位
     */
    private final long mostSigBits;

    /**
     * 此UUID的最低64有效位
     */
    private final long leastSigBits;

    @Override
    public int compareTo(UUID val) {
        return (Long.compare(this.leastSigBits, val.leastSigBits));
    }


    /**
     * SecureRandom 的单例
     */
    private static class Holder {
        static final SecureRandom NUMBER_GENERATOR = getSecureRandom();
    }

    /**
     * 获取类型 4（伪随机生成的）UUID 的静态工厂。 使用加密的强伪随机数生成器生成该 UUID。
     *
     * @return 随机生成的 {@code UUID}
     */
    public static UUID randomUuid() {
        return randomUuid(true);
    }


    /**
     * 获取类型 4（伪随机生成的）UUID 的静态工厂。 使用加密的本地线程伪随机数生成器生成该 UUID。
     *
     * @return 随机生成的 {@code UUID}
     */
    public static UUID fastUuid() {
        return randomUuid(false);
    }

    /**
     * 获取类型 4（伪随机生成的）UUID 的静态工厂。 使用加密的强伪随机数生成器生成该 UUID。
     *
     * @param isSecure 是否使用{@link SecureRandom}如果是可以获得更安全的随机码，否则可以得到更好的性能
     * @return 随机生成的 {@code UUID}
     */
    public static UUID randomUuid(boolean isSecure) {

        final Random ng = isSecure ? Holder.NUMBER_GENERATOR : getRandom();

        byte[] randomBytes = new byte[16];
        ng.nextBytes(randomBytes);
        // clear version
        randomBytes[6] &= 0x0f;
        // set to version 4
        randomBytes[6] |= 0x40;
        // clear variant
        randomBytes[8] &= 0x3f;
        // set to IETF variant
        randomBytes[8] |= 0x80;
        return new UUID(randomBytes);
    }

    /**
     * 私有构造
     *
     * @param data 数据
     */
    private UUID(byte[] data) {
        int eight = 8;
        int sixteen = 16;
        long msb = 0;
        long lsb = 0;
        assert data.length == sixteen : "data must be 16 bytes in length";
        for (int i = 0; i < eight; i++) {
            msb = (msb << eight) | (data[i] & 0xff);
        }
        for (int i = eight; i < sixteen; i++) {
            lsb = (lsb << eight) | (data[i] & 0xff);
        }
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }


    /**
     * 返回此{@code UUID} 的字符串表现形式。
     *
     * <p>
     * UUID 的字符串表示形式由此 BNF 描述：
     *
     * <pre>
     * {@code
     * UUID                   = <time_low>-<time_mid>-<time_high_and_version>-<variant_and_sequence>-<node>
     * time_low               = 4*<hexOctet>
     * time_mid               = 2*<hexOctet>
     * time_high_and_version  = 2*<hexOctet>
     * variant_and_sequence   = 2*<hexOctet>
     * node                   = 6*<hexOctet>
     * hexOctet               = <hexDigit><hexDigit>
     * hexDigit               = [0-9a-fA-F]
     * }
     * </pre>
     *
     * </blockquote>
     *
     * @param isSimple 是否简单模式，简单模式为不带'-'的UUID字符串
     * @return 此{@code UUID} 的字符串表现形式
     */
    public String toString(boolean isSimple) {
        final StringBuilder builder = new StringBuilder(isSimple ? 32 : 36);
        // time_low
        builder.append(digits(mostSigBits >> 32, 8));
        if (!isSimple) {
            builder.append('-');
        }
        // time_mid
        builder.append(digits(mostSigBits >> 16, 4));
        if (!isSimple) {
            builder.append('-');
        }
        // time_high_and_version
        builder.append(digits(mostSigBits, 4));
        if (!isSimple) {
            builder.append('-');
        }
        // variant_and_sequence
        builder.append(digits(leastSigBits >> 48, 4));
        if (!isSimple) {
            builder.append('-');
        }
        // node
        builder.append(digits(leastSigBits, 12));

        return builder.toString();
    }

    /**
     * 返回指定数字对应的hex值
     *
     * @param val    值
     * @param digits 位
     * @return 值
     */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }


    /**
     * 获取{@link SecureRandom}，类提供加密的强随机数生成器 (RNG)
     *
     * @return {@link SecureRandom}
     */
    public static SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取随机数生成器对象
     * ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
     *
     * @return {@link ThreadLocalRandom}
     */
    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }
}
