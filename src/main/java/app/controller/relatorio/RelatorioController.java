package app.controller.relatorio;

import app.dto.relatorioDto.FiltroDataRequestDTO;
import app.model.Pedidos;
import app.model.Produto;
import app.relatorio.Estoque;
import app.relatorio.RelatorioPedidos;
import app.repository.PedidosRepository;
import app.repository.ProdutoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidosRepository pedidoRepository;


    /* Código que o matheus enviou

    @GetMapping("/administrativo/vendas")
    public ModelAndView relatoriosVendas(){
        ModelAndView model = new ModelAndView("/administrativo/relatorio/vendas");
        return model;
    }
     */

    @GetMapping("/produtos")
    public ResponseEntity<byte[]> gerarRelatorio() throws JRException, FileNotFoundException {
        List<Estoque> lista = new ArrayList<>();
        try {

            for (Produto p : produtoRepository.findAll()) {
                Estoque e = new Estoque();
                e.setNome(p.getNome());
                e.setValorUnitario(p.getValorVenda());
                e.setQuantidade(p.getQuantidadeEstoque());
                e.setValorTotal(p.getQuantidadeEstoque() * p.getValorVenda());
                lista.add(e);
            }
            Map<String, Object> empParams = new HashMap<String, Object>();
            empParams.put("Araujo", "app");
            empParams.put("Estoque", new JRBeanCollectionDataSource(lista));

            JasperPrint empReport =
                    JasperFillManager.fillReport
                            (
                                    JasperCompileManager.compileReport(
                                            ResourceUtils
                                                    .getFile("classpath:Estoque.jrxml")
                                                    .getAbsolutePath()) // path of the jasper report
                                    , empParams // dynamic parameters
                                    , new JRBeanCollectionDataSource(lista)
                            );

            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "Estoque.pdf");
            //create the report in PDF format
            return new ResponseEntity<byte[]>
                    (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pedidos")
    public ResponseEntity<byte[]> gerarRelatorioPedidos(FiltroDataRequestDTO dto) throws JRException, FileNotFoundException {
        List<RelatorioPedidos> lista = new ArrayList<>();
        try {

            for (Pedidos p : pedidoRepository.findAllPedidos(dto.getDataInicio(), dto.getDataFinal())) {
                RelatorioPedidos pedidos = new RelatorioPedidos();
                pedidos.setDataCompra(p.getDataCompra().toString());
                pedidos.setFormaPagamento(p.getFormaPagmento());
                pedidos.setValorTotal(p.getValorTotal());
                lista.add(pedidos);
            }
            Map<String, Object> empParams = new HashMap<String, Object>();
            empParams.put("Araujo", "app");
            empParams.put("RelatorioPedidos", new JRBeanCollectionDataSource(lista));

            JasperPrint empReport =
                    JasperFillManager.fillReport
                            (
                                    JasperCompileManager.compileReport(
                                            ResourceUtils
                                                    .getFile("classpath:Pedidos.jrxml")
                                                    .getAbsolutePath()) // path of the jasper report
                                    , empParams // dynamic parameters
                                    , new JRBeanCollectionDataSource(lista)
                            );

            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "Pedidos.pdf");
            //create the report in PDF format
            return new ResponseEntity<byte[]>
                    (JasperExportManager.exportReportToPdf(empReport), headers, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
