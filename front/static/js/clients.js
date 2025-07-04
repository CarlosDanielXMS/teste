document.addEventListener('DOMContentLoaded', () => {
    const clientesModule = document.getElementById('clientes-module');

    // Data simulada
    let clientes = [
        { id: 1, nome: 'Ana Silva', telefone: '(11) 98765-4321', email: 'ana@email.com', status: 1 },
        { id: 2, nome: 'Bruno Costa', telefone: '(21) 91234-5678', email: 'bruno@email.com', status: 1 },
        { id: 3, nome: 'Carla Souza', telefone: '(31) 99887-7665', email: 'carla@email.com', status: 0 },
        { id: 4, nome: 'Daniel Pereira', telefone: '(11) 91122-3344', email: 'daniel@email.com', status: 1 }
    ];

    let nextClientId = clientes.length > 0 ? Math.max(...clientes.map(c => c.id)) + 1 : 1;

    // Renderiza o módulo de clientes
    function renderClientesModule() {
        clientesModule.innerHTML = `
            <div class="module-header">
                <h2>Clientes</h2>
                <button class="btn btn-primary" id="add-cliente-btn">
                    <span class="material-icons">add</span> Novo Cliente
                </button>
            </div>
            <div class="module-controls">
                <input type="text" id="search-cliente" placeholder="Buscar cliente por nome ou email...">
                <select id="filter-cliente-status">
                    <option value="all">Todos</option>
                    <option value="1">Ativos</option>
                    <option value="0">Inativos</option>
                </select>
            </div>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Telefone</th>
                        <th>E-mail</th>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody id="clientes-list-body">
                    </tbody>
            </table>

            <div id="cliente-modal" class="modal">
                <div class="modal-content">
                    <span class="close-button" onclick="hideModal('cliente-modal')">&times;</span>
                    <h3 id="modal-title">Cadastrar Cliente</h3>
                    <form id="cliente-form">
                        <input type="hidden" id="cliente-id">
                        <div class="form-group">
                            <label for="cliente-nome">Nome</label>
                            <input type="text" id="cliente-nome" required>
                        </div>
                        <div class="form-group">
                            <label for="cliente-telefone">Telefone</label>
                            <input type="text" id="cliente-telefone" required>
                        </div>
                        <div class="form-group">
                            <label for="cliente-email">E-mail</label>
                            <input type="email" id="cliente-email" required>
                        </div>
                        <div class="form-group">
                            <label for="cliente-senha">Senha (deixe em branco para manter)</label>
                            <input type="password" id="cliente-senha">
                        </div>
                        <div class="form-group">
                            <label for="cliente-status">Status</label>
                            <select id="cliente-status">
                                <option value="1">Ativo</option>
                                <option value="0">Inativo</option>
                            </select>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn btn-outline" onclick="hideModal('cliente-modal')">Cancelar</button>
                            <button type="submit" class="btn btn-primary">Salvar</button>
                        </div>
                    </form>
                </div>
            </div>
        `;

        // Adiciona event listeners após o HTML ser renderizado
        document.getElementById('add-cliente-btn').addEventListener('click', openAddClienteModal);
        document.getElementById('cliente-form').addEventListener('submit', handleClienteFormSubmit);
        document.getElementById('search-cliente').addEventListener('input', filterClientes);
        document.getElementById('filter-cliente-status').addEventListener('change', filterClientes);

        listClientes(); // Carrega a lista inicial de clientes
    }

    function listClientes() {
        const tbody = document.getElementById('clientes-list-body');
        tbody.innerHTML = ''; // Limpa a lista existente

        const searchTerm = document.getElementById('search-cliente')?.value.toLowerCase() || '';
        const statusFilter = document.getElementById('filter-cliente-status')?.value || 'all';

        const filteredClientes = clientes.filter(cliente => {
            const matchesSearch = cliente.nome.toLowerCase().includes(searchTerm) ||
                                  cliente.email.toLowerCase().includes(searchTerm);
            const matchesStatus = statusFilter === 'all' || cliente.status.toString() === statusFilter;
            return matchesSearch && matchesStatus;
        });


        if (filteredClientes.length === 0) {
            tbody.innerHTML = `<tr><td colspan="6" style="text-align: center; padding: 20px;">Nenhum cliente encontrado.</td></tr>`;
            return;
        }

        filteredClientes.forEach(cliente => {
            const row = tbody.insertRow();
            row.innerHTML = `
                <td>${cliente.id}</td>
                <td>${cliente.nome}</td>
                <td>${cliente.telefone}</td>
                <td>${cliente.email}</td>
                <td>
                    <span class="status-badge ${cliente.status === 1 ? 'status-active' : 'status-inactive'}">
                        ${cliente.status === 1 ? 'Ativo' : 'Inativo'}
                    </span>
                </td>
                <td class="actions">
                    <button class="btn-icon-only" title="Editar" onclick="openEditClienteModal(${cliente.id})">
                        <span class="material-icons">edit</span>
                    </button>
                    <button class="btn-icon-only" title="Excluir" onclick="deleteCliente(${cliente.id})">
                        <span class="material-icons">delete</span>
                    </button>
                </td>
            `;
        });
    }

    function filterClientes() {
        listClientes(); // Re-renderiza a lista com os filtros aplicados
    }

    function openAddClienteModal() {
        document.getElementById('modal-title').textContent = 'Cadastrar Cliente';
        document.getElementById('cliente-id').value = '';
        document.getElementById('cliente-nome').value = '';
        document.getElementById('cliente-telefone').value = '';
        document.getElementById('cliente-email').value = '';
        document.getElementById('cliente-senha').value = ''; // Senha vazia para novo cadastro
        document.getElementById('cliente-status').value = '1'; // Default para ativo
        showModal('cliente-modal');
    }

    function openEditClienteModal(id) {
        const cliente = clientes.find(c => c.id === id);
        if (cliente) {
            document.getElementById('modal-title').textContent = 'Editar Cliente';
            document.getElementById('cliente-id').value = cliente.id;
            document.getElementById('cliente-nome').value = cliente.nome;
            document.getElementById('cliente-telefone').value = cliente.telefone;
            document.getElementById('cliente-email').value = cliente.email;
            document.getElementById('cliente-senha').value = ''; // Senha sempre vazia para edição, preenchida só se mudar
            document.getElementById('cliente-status').value = cliente.status;
            showModal('cliente-modal');
        } else {
            alert('Cliente não encontrado.');
        }
    }

    function handleClienteFormSubmit(event) {
        event.preventDefault();
        const id = document.getElementById('cliente-id').value;
        const nome = document.getElementById('cliente-nome').value;
        const telefone = document.getElementById('cliente-telefone').value;
        const email = document.getElementById('cliente-email').value;
        const senha = document.getElementById('cliente-senha').value; // Apenas se houver valor
        const status = parseInt(document.getElementById('cliente-status').value);

        if (id) {
            // Editar cliente existente
            const clienteIndex = clientes.findIndex(c => c.id === parseInt(id));
            if (clienteIndex > -1) {
                clientes[clienteIndex].nome = nome;
                clientes[clienteIndex].telefone = telefone;
                clientes[clienteIndex].email = email;
                if (senha) clientes[clienteIndex].senha = senha; // Atualiza senha apenas se preenchida
                clientes[clienteIndex].status = status;
                alert('Cliente atualizado com sucesso!');
            }
        } else {
            // Cadastrar novo cliente
            const novoCliente = {
                id: nextClientId++,
                nome,
                telefone,
                email,
                senha,
                status
            };
            clientes.push(novoCliente);
            alert('Cliente cadastrado com sucesso!');
        }
        hideModal('cliente-modal');
        listClientes(); // Atualiza a lista
    }

    function deleteCliente(id) {
        if (confirm('Tem certeza que deseja excluir este cliente?')) {
            clientes = clientes.filter(c => c.id !== id);
            alert('Cliente excluído!');
            listClientes(); // Atualiza a lista
        }
    }

    // Exporta funções para serem acessíveis globalmente (para onclick no HTML)
    window.openEditClienteModal = openEditClienteModal;
    window.deleteCliente = deleteCliente;
    window.showModal = showModal; // Re-exportando do main.js para garantir acessibilidade
    window.hideModal = hideModal;

    // Quando o módulo de clientes for ativado, renderiza-o
    document.querySelector('.sidebar-item[data-module="clientes"]').addEventListener('click', renderClientesModule);

    // Initial render if clients module is active on load (e.g., if it's the default view)
    if (clientesModule.classList.contains('active')) {
        renderClientesModule();
    }
});