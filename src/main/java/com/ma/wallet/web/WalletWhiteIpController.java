package com.ma.wallet.web;
import com.ma.wallet.core.vo.Result;
import com.ma.wallet.core.utils.ResultGenerator;
import com.ma.wallet.model.WalletWhiteIp;
import com.ma.wallet.service.WalletWhiteIpService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by FengBin on 2017-08-22.
*/
@RestController
@RequestMapping("/wallet/white/ip")
public class WalletWhiteIpController {
    @Resource
    private WalletWhiteIpService walletWhiteIpService;

    @PostMapping("/add")
    public Result add(WalletWhiteIp walletWhiteIp) {
        walletWhiteIpService.save(walletWhiteIp);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        walletWhiteIpService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(WalletWhiteIp walletWhiteIp) {
        walletWhiteIpService.update(walletWhiteIp);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        WalletWhiteIp walletWhiteIp = walletWhiteIpService.findById(id);
        return ResultGenerator.genSuccessResult(walletWhiteIp);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<WalletWhiteIp> list = walletWhiteIpService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
