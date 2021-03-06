package com.china.world.service.impl;

import com.china.world.config.captcha.VerifyCodeConfig;
import com.china.world.constant.Constants;
import com.china.world.domain.SystemRole;
import com.china.world.domain.SystemUser;
import com.china.world.excetpion.ServiceException;
import com.china.world.excetpion.captcha.CaptchaExpireException;
import com.china.world.excetpion.user.UserPasswordNotMatchException;
import com.china.world.manager.AsyncManager;
import com.china.world.manager.factory.AsyncFactory;
import com.china.world.mapper.SystemRoleMapper;
import com.china.world.mapper.SystemUserMapper;
import com.china.world.model.LoginUser;
import com.china.world.service.LoginService;
import com.china.world.service.RedisService;
import com.china.world.service.SystemMenuService;
import com.china.world.service.TokenService;
import com.china.world.utils.AjaxResult;
import com.china.world.utils.IpUtils;
import com.china.world.utils.MessageUtils;
import com.china.world.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Peng
 * @date 2022/3/2 11:53
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private VerifyCodeConfig verifyCodeConfig;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemRoleMapper systemRoleMapper;
    @Autowired
    private SystemMenuService systemMenuService;

    @Override
    public AjaxResult login(String username, String password, String code, String uuid) {
        boolean captchaOnOff = verifyCodeConfig.isCaptchaOnOff();
        // ???????????????
        if (captchaOnOff) {
            validateCaptcha(username, code, uuid);
        }
        logger.info("?????????????????????!");
        AjaxResult ajax = AjaxResult.success();
        // ????????????
        Authentication authentication;
        try {
            // ?????????????????????UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            logger.error("??????????????????: {}", e.getMessage());
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        ajax.put(Constants.TOKEN, tokenService.createToken(loginUser));
        return ajax;
    }


    @Override
    public AjaxResult getInfo() {
        AjaxResult ajax = AjaxResult.success();
        try {
            // ??????????????????
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            SystemUser user = loginUser.getUser();
            // ????????????
            Set<String> roles = new HashSet<>();
            // ????????????
            Set<String> permissions = systemMenuService.getMenuPermission(user);
            // ???????????????????????????
            if (user.isAdmin()) {
                roles.add("admin");
                permissions.add("*:*:*");
            } else {
                List<SystemRole> rolePerms = systemRoleMapper.selectRolePermissionByUserId(user.getUserId());
                for (SystemRole perm : rolePerms) {
                    if (null != perm) {
                        roles.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
                    }
                }
            }
            ajax.put("user", user);
            ajax.put("roles", roles);
            ajax.put("permissions", permissions);
        } catch (Exception e) {
            logger.error("??????????????????: {}", e.getMessage());
        }
        return ajax;
    }

    /**
     * ???????????????
     *
     * @param username ?????????
     * @param code     ?????????
     * @param uuid     ????????????
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLoginInfo(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new CaptchaExpireException();
        }
    }

    /**
     * ??????????????????
     *
     * @param userId ??????ID
     */
    public void recordLoginInfo(Long userId) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUserId(userId);
        systemUser.setLoginIp(IpUtils.getIpAddress(ServletUtils.getRequest()));
        systemUser.setLoginDate(LocalDateTime.now());
        systemUserMapper.updateIpAndTimeById(systemUser);
    }
}
