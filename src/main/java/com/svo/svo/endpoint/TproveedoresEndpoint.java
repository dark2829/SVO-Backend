package com.svo.svo.endpoint;

import com.svo.svo.model.TproveedoresDTO;
import com.svo.svo.service.TproveedoresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/proveedores")
public class TproveedoresEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(TproveedoresEndpoint.class);

    @Autowired
    private TproveedoresService tproveedoresService;

    @GetMapping("/findAllProveedores") //Manda al Front
    public ResponseEntity<List<TproveedoresDTO>> findAllProveedores(){
        List<TproveedoresDTO> listProveedores = null;
        LOG.info("findAllProveedores()");
        try{
            listProveedores= tproveedoresService.findAllProveedores();

        }catch (Exception e){
            new ResponseEntity<Exception>(e,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<TproveedoresDTO>>(listProveedores, HttpStatus.ACCEPTED);
    }

    @PostMapping("/insert")
    public void insert(@RequestBody TproveedoresDTO tproveedoresDTO) {
        LOG.info("insert()->Proveedor: {}", tproveedoresDTO);
        try {
            tproveedoresService.insert(tproveedoresDTO);
            LOG.info("Proveedor guardado correctamente");
        } catch (Exception e) {
           LOG.error(String.valueOf(e));
        }
    }

    @PostMapping("/update")
    public void update(@RequestParam("id") int id, @RequestBody Map<String,String> data){
        LOG.info("update()->id: {} data: {}",id,data);
        try {
            tproveedoresService.update((long) id, data);
        } catch (Exception e){
            LOG.error(String.valueOf(e));
        }
    }


}
