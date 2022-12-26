package org.sid.billingservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RefreshScope //refresher la configuration a chaque fois ça change pas besoin d'utiliser actuator ça fait automatiquement
public class ConsulConfigRestController {

    @Autowired
    private MyConsulConfig myConsulConfig;
    @Autowired
    private MyVaultConfig myVaultConfig;

    //@Value("${token.accessTokenTimeout}")
    //private long accessTokenTimeout;
    //@Value("${token.refreshTokenTimeout}")
    //private long refreshTokenTimeout;

    @GetMapping("/myConfig") // Object soit MyConsulConfig ou  MyVaultConfig
    public Map<String, Object> myConfig(){
        return Map.of("consulConfig", myConsulConfig, "vaultConfig", myVaultConfig);
    }
}

