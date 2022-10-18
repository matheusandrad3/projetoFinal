package app.service;

import app.model.Produto;
import app.model.enums.DisponibilidadeProduto;
import app.repository.CategoriaRepository;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ClienteService service;

    @Autowired
    CategoriaService categoriaService;

    public Produto cadastrarProduto(Produto produto) {
        produto.setDisponibilidade(DisponibilidadeProduto.DISPONIVEL);
        return repository.save(produto);
    }


    public List<Produto> listarProdutos() {
        return repository.findAll();
    }


    public void deletarProduto(Long id) {
        repository.deleteById(id);
    }

    public Optional<Produto> atualizarProduto(Long id) {
        return repository.findById(id);
    }

}
