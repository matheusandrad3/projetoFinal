package app.service;

import app.exeception.AraujoExeception;
import app.model.Produto;
import app.model.enums.DisponibilidadeProduto;
import app.repository.CategoriaRepository;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public void consumirEstoque(Long id, Integer quantidade) {
        Produto p = repository.getById(id);
        if (p.getQuantidadeEstoque() >= quantidade) {
            p.setQuantidadeEstoque(p.getQuantidadeEstoque() - quantidade);
            repository.save(p);
        } else {
            throw new AraujoExeception("Quantidade indisponível!", HttpStatus.NOT_FOUND);
        }
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
