package cdst_be_marche.adapder.integrazioni.suap.controller;

import cdst_be_marche.adapder.integrazioni.suap.service.IntegProtocolService;
import cdst_be_marche.protocollo.DTO.IntegProtocolloDTO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Lazy
@RequestMapping({"/integrazioni/testprotocollo"})
public class IntegSuapFrontieraProtocolloController
{
  @Autowired
  private IntegProtocolService protocolService;
  
  @RequestMapping(value={"submitprotocollo/{suapPraticaId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ResponseEntity<String> testrestclient(@PathVariable Integer suapPraticaId, HttpServletRequest request)
  {
    ResponseEntity<String> response = null;
    try
    {
      IntegProtocolloDTO integDTO = new IntegProtocolloDTO();
      integDTO.setIdPratica(suapPraticaId);
      this.protocolService.submitProtocol();
      response = new ResponseEntity(HttpStatus.OK);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return response;
  }
}
