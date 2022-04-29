package com.svo.svo.endpoint;

import com.svo.svo.model.TproductosDTO;
import com.svo.svo.model.TproveedoresDTO;
import com.svo.svo.service.TproductosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class TproductosEndPoint {
    private static final Logger LOG = LoggerFactory.getLogger(TproductosEndPoint.class);

    @Autowired
    private TproductosService tproductosService;

    @PostMapping("/insertProduct")
    public void insertProduct(@RequestBody TproductosDTO tproductosDTO) {
        LOG.info("<<<<<insertProducto() -> ", tproductosDTO);
        try {
            tproductosService.insert(tproductosDTO);
        } catch (Exception e) {
        }
    }

    @PostMapping("/update")
    public void update(@RequestParam("id") int id, @RequestBody Map<String,String> data){
        LOG.info("update()->id: {} data: {}",id,data);
        try {
            tproductosService.update((long) id, data);
        } catch (Exception e){
            LOG.error(String.valueOf(e));
        }
    }

    @GetMapping("/findAllProductos")
    public ResponseEntity<List<TproductosDTO>> findAllProveedores(){
        List<TproductosDTO> listProductos = null;
        LOG.info("findAllProductos()");
        try{
            listProductos= tproductosService.findAllProductos();

        }catch (Exception e){
            new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<TproductosDTO>>(listProductos, HttpStatus.ACCEPTED);
    }
}
