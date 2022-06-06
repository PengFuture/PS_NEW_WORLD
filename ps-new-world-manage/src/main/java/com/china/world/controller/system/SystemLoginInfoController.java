package com.china.world.controller.system;

import com.china.world.domain.SystemLoginInfo;
import com.china.world.qo.SystemLoginInfoQo;
import com.china.world.service.SystemLoginInfoService;
import com.china.world.utils.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Peng
 * @date 2022/3/8 11:26
 */
@RestController
@RequestMapping("/monitor/loginInfo")
public class SystemLoginInfoController {

    @Autowired
    private SystemLoginInfoService systemLoginInfoService;

    @PreAuthorize("hasAuthority('monitor:loginInfo:list')")
    @GetMapping("/list.do")
    public TableDataInfo<SystemLoginInfo> list(SystemLoginInfoQo qo) {
        return systemLoginInfoService.selectSystemLoginInfoList(qo);
    }

}
