package com.example.controller;

import com.example.service.LicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Sparkler on 08/11/2017.
 */
@Controller
class LicenceController {

    @Autowired
    private LicenceService licenceService;

    @RequestMapping(value = "/licence", method = RequestMethod.POST)
    @ResponseBody
    public String addPerson(@RequestParam("command") String command, @RequestParam("data") String data, HttpServletRequest request) {

            Date now = new Date();
            String ip = request.getRemoteAddr();

        if (command.equals("activate")){

            return licenceService.activate(data, ip);

        }else if (command.equals("report")){
//            JSONObject jsonData = JSON.parse(params.data)
//
//            String device_id = jsonData.get('device_id')
//            //TODO pueden ser null esos
//            String licence_number = jsonData.get('licence_number')
//            String client_number = jsonData.get('client_number')
//            String[] users = jsonData.get('users')
//            String version = jsonData.get('version')
//            String model = jsonData.get('model')
//            String language = jsonData.get('language')
//
//            Dispositivo dispositivo = verifyDispositivo(new Dispositivo(hadwareId: device_id, users: users, version: version, model: model, language: language))
//            Reporte reporte = new Reporte(cuando: now, dispositivo: dispositivo, ip: ip)
//
//            def clienteSel = Cliente.findByNroCliente(client_number)
//            if (clienteSel != null) {
//                def licenciaSel = Licencia.where {
//                    nroLicencia == licence_number && cliente == clienteSel && banned == false
//                }.get()
//                if (licenciaSel.banned){
//                    //TODO descativar
//                }else{
//                    //TODO guardar
//                }
//            }
            return "wrong seed";
        }else{
            return "wrong seed";
        }


    }



}
