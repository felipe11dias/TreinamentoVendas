package io.github.felipe11dias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.felipe11dias.domain.entity.Cliente;

@Repository
public interface Clientes extends JpaRepository<Cliente, Integer> {
	
	List<Cliente> findByNomeLike(String nome);
	
	void deleteByNome(String nome);
	
	boolean existsByNome(String nome);
	
	@Query(" select c from Cliente c left join fetch c.pedidos p where c.id = :id")
	Cliente findClienteFecthPedidos(@Param("id") Integer id);
	
	/*
	 * UTILIZANDO ENTITY MANAGER
	 * 
	
	private static String INSERT = "insert into cliente (nome) values (?)";
	private static String UPDATE = "update cliente set nome = ? where id = ?";
	private static String SELECT_ALL = "select * from cliente";
	private static String DELETE = "delete from cliente where id = ?";
	
	@Autowired
	private EntityManager entityManager;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		entityManager.persist(cliente);
		return cliente;
	}
	
	@Transactional
	public Cliente atualizar(Cliente cliente) {
		entityManager.merge(cliente);
		return cliente;
	}
	
	@Transactional
	public void deletar(Integer id) {
		Cliente cliente = entityManager.find(Cliente.class, id);
		deletar(cliente);
	}
	
	@Transactional
	public void deletar(Cliente cliente) {
		if(!entityManager.contains(cliente)) {
			cliente = entityManager.merge(cliente);
		}
		entityManager.remove(cliente);
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> buscarPorNome(String nome) {
		String jpql = " select c from Cliente c where c.nome = :nome ";
		TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> obterTodos() {
		return entityManager.createQuery(" from Cliente" ,Cliente.class).getResultList();
	}
	
	* UTILIZANDO JDBC TEMPLATE
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Cliente salvar(Cliente cliente) {
		jdbcTemplate.update(INSERT, new Object[]{cliente.getNome()});
		return cliente;
	}
	
	public Cliente atualizar(Cliente cliente) {
		jdbcTemplate.update(UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
		return cliente;
	}
	
	public void deletar(Integer id) {
		jdbcTemplate.update(DELETE, new Object[]{id});
	}
	
	public List<Cliente> buscarPorNome(String nome) {
		return jdbcTemplate.query(SELECT_ALL.concat(" where nome like ?"), new Object[]{"%" + nome + "%"}, obterClientesMapper());
	}
	
	public List<Cliente> obterTodos() {
		return jdbcTemplate.query(SELECT_ALL, obterClientesMapper());
	}

	private RowMapper<Cliente> obterClientesMapper() {
		return new RowMapper<Cliente>() {
			@Override
			public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Cliente(rs.getString("nome"));
			}
		};
	}
	*/
}
