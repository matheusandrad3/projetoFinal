package app.service;

import app.model.Produto;
import app.model.enums.DisponibilidadeProduto;
import app.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto cadastrarProduto(Produto produto) {
        if (produto.getQuantidadeEstoque() <= 0) {
            produto.setDisponibilidade(DisponibilidadeProduto.INDISPONIVEL);
        } else {
            produto.setDisponibilidade(DisponibilidadeProduto.DISPONIVEL);
        }
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutosDisponivel() {
        return produtoRepository.findAllDisponivel();
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public Optional<Produto> atualizarProduto(Long id) {
        return produtoRepository.findById(id);
    }
}
