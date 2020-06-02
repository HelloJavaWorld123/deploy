package com.lk.deploy.cmd;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * @author : RXK
 * Date : 2020/4/7 15:01
 * Desc:
 */
@RestController
@RequestMapping("/cmd")
public class CmdController {

    @GetMapping("/build")
    public ResponseEntity<String> exec(@RequestParam(value = "dir",required = true) String dir){
        if (StringUtils.isEmpty(dir)) {
            return ResponseEntity.badRequest().body("缺少参数");
        }
        String file_path = "/home/travis/repository"+dir;
        File file = new File(file_path);
        String[] cmds = new String[]{"sh","./build.sh"};
        try {
            Process process = Runtime.getRuntime().exec(cmds, null, file);
            int waitFor = process.waitFor();
            return ResponseEntity.ok().body("执行的结果是：{" + waitFor + "}");
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getLocalizedMessage());
        }
    }
}
