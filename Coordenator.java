import entities.*;
import repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Coordenator {

    private final Scanner input = new Scanner(System.in);

    private final ClienteRepository clienteRepo;
    private final ProfissionalRepository profRepo;
    private final ServicoRepository servRepo;
    private final CatalogoRepository catRepo;
    private final AgendaRepository agRepo;
    private final ServicoAgendadoRepository saRepo;

    public Coordenator() throws Exception {
        clienteRepo = new ClienteRepository();
        profRepo = new ProfissionalRepository();
        servRepo = new ServicoRepository();
        catRepo = new CatalogoRepository();
        agRepo = new AgendaRepository();
        saRepo = new ServicoAgendadoRepository();
    }

    public void run() {
        while (true) {
            clearScreen();
            println("=== SISTEMA DE AGENDAMENTOS ===");
            println("1. Clientes");
            println("2. Profissionais");
            println("3. Serviços");
            println("4. Catálogo");
            println("5. Agendas");
            println("0. Sair");
            switch (readInt("Opção: ")) {
                case 1:
                    manageClientes();
                    break;
                case 2:
                    manageProfissionais();
                    break;
                case 3:
                    manageServicos();
                    break;
                case 4:
                    manageCatalogo();
                    break;
                case 5:
                    manageAgendas();
                    break;
                case 0:
                    println("Encerrando...");
                    return;
                default:
                    println("Opção inválida.");
                    pause();
            }
        }
    }

    private Cliente escolherCliente() {
        try {
            println("\n== Escolha um Cliente ==");
            clienteRepo.listarTodos()
                    .forEach(cli -> println(" [" + cli.getId() + "] " + cli.getNome()));
            int idCliente = readInt("ID do cliente: ");
            return clienteRepo.buscarPorId(idCliente);
        } catch (Exception e) {
            println("Erro ao carregar clientes: " + e.getMessage());
            return null;
        }
    }

    private Profissional escolherProfissional() {
        try {
            println("\n== Escolha um Profissional ==");
            profRepo.listarTodos()
                    .forEach(profissional -> println(" [" + profissional.getId() + "] " + profissional.getNome()));
            int idProfissional = readInt("ID do profissional: ");
            return profRepo.buscarPorId(idProfissional);
        } catch (Exception e) {
            println("Erro ao carregar profissionais: " + e.getMessage());
            return null;
        }
    }

    private Servico escolherServico() {
        try {
            println("\n== Escolha um Serviço ==");
            servRepo.listarTodos()
                    .forEach(servico -> println(" [" + servico.getId() + "] " + servico.getDescricao()));
            int idServico = readInt("ID do serviço: ");
            return servRepo.buscarPorId(idServico);
        } catch (Exception e) {
            println("Erro ao carregar serviços: " + e.getMessage());
            return null;
        }
    }

    private Agenda escolherAgenda() {
        try {
            println("\n== Escolha uma Agenda ==");
            agRepo.listarTodos()
                    .forEach(agenda -> println(" [" + agenda.getId() + "] "
                            + agenda.getCliente().getNome() + " | " + agenda.getDataHora()));
            int idAgenda = readInt("ID da agenda: ");
            return agRepo.buscarPorId(idAgenda);
        } catch (Exception e) {
            println("Erro ao carregar agendas: " + e.getMessage());
            return null;
        }
    }

    private void manageClientes() {
        while (true) {
            menuHeader("CLIENTES");
            println("1. Cadastrar");
            println("2. Listar");
            println("3. Atualizar");
            println("4. Excluir");
            println("0. Voltar");
            switch (readInt("Opção: ")) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    excluirCliente();
                    break;
                case 0:
                    return;
                default:
                    println("Opção inválida.");
            }
            pause();
        }
    }

    private void cadastrarCliente() {
        try {
            String nome = readString("Nome: ");
            String telefone = readString("Telefone: ");
            String email = readString("Email: ");
            String senha = readString("Senha: ");
            Cliente cliente = new Cliente(0, nome, telefone, email, senha, 1);
            clienteRepo.incluir(cliente);
            println("Cliente cadastrado!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void listarClientes() {
        try {
            clienteRepo.listarTodos().forEach(System.out::println);
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void atualizarCliente() {
        Cliente cliente = escolherCliente();
        if (cliente == null) {
            println("Cliente não encontrado.");
            return;
        }
        try {
            String novoNome = readString("Nome (" + cliente.getNome() + "): ");
            String novoTel = readString("Telefone (" + cliente.getTelefone() + "): ");
            String novoEmail = readString("Email (" + cliente.getEmail() + "): ");
            String novaSenha = readString("Senha (*****): ");
            int novoStatus = readInt("Status [" + cliente.getStatus() + "]: ");

            if (!novoNome.isEmpty())
                cliente.setNome(novoNome);
            if (!novoTel.isEmpty())
                cliente.setTelefone(novoTel);
            if (!novoEmail.isEmpty())
                cliente.setEmail(novoEmail);
            if (!novaSenha.isEmpty())
                cliente.setSenha(novaSenha);
            cliente.setStatus(novoStatus);

            clienteRepo.alterar(cliente);
            println("Cliente atualizado!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void excluirCliente() {
        Cliente cliente = escolherCliente();
        if (cliente == null) {
            println("Cliente não encontrado.");
            return;
        }
        try {
            boolean removido = clienteRepo.excluir(cliente.getId());
            println(removido ? "Cliente excluído!" : "Nenhum registro removido.");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void manageProfissionais() {
        while (true) {
            menuHeader("PROFISSIONAIS");
            println("1. Cadastrar");
            println("2. Listar");
            println("3. Atualizar");
            println("4. Excluir");
            println("0. Voltar");
            switch (readInt("Opção: ")) {
                case 1:
                    cadastrarProfissional();
                    break;
                case 2:
                    listarProfissionais();
                    break;
                case 3:
                    atualizarProfissional();
                    break;
                case 4:
                    excluirProfissional();
                    break;
                case 0:
                    return;
                default:
                    println("Opção inválida.");
            }
            pause();
        }
    }

    private void cadastrarProfissional() {
        try {
            String nome = readString("Nome: ");
            String telefone = readString("Telefone: ");
            String email = readString("Email: ");
            String senha = readString("Senha: ");
            double salFixo = readDouble("Salário fixo: ");
            double comissao = readDouble("Comissão (%): ");

            Profissional profissional = new Profissional(0, nome, telefone, email, senha, 1, salFixo, comissao);
            profRepo.incluir(profissional);
            println("Profissional cadastrado!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void listarProfissionais() {
        try {
            profRepo.listarTodos().forEach(System.out::println);
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void atualizarProfissional() {
        Profissional profissional = escolherProfissional();
        if (profissional == null) {
            println("Profissional não encontrado.");
            return;
        }
        try {
            String novoNome = readString("Nome (" + profissional.getNome() + "): ");
            String novoTel = readString("Telefone (" + profissional.getTelefone() + "): ");
            String novoEmail = readString("Email (" + profissional.getEmail() + "): ");
            String novaSenha = readString("Senha (*****): ");
            double novoSalario = readDouble("Salário (" + profissional.getSalarioFixo() + "): ");
            double novaComissao = readDouble("Comissão (" + profissional.getComissao() + "): ");
            int novoStatus = readInt("Status [" + profissional.getStatus() + "]: ");

            if (!novoNome.isEmpty())
                profissional.setNome(novoNome);
            if (!novoTel.isEmpty())
                profissional.setTelefone(novoTel);
            if (!novoEmail.isEmpty())
                profissional.setEmail(novoEmail);
            if (!novaSenha.isEmpty())
                profissional.setSenha(novaSenha);
            profissional.setSalarioFixo(novoSalario);
            profissional.setComissao(novaComissao);
            profissional.setStatus(novoStatus);

            profRepo.alterar(profissional);
            println("Profissional atualizado!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void excluirProfissional() {
        Profissional profissional = escolherProfissional();
        if (profissional == null) {
            println("Profissional não encontrado.");
            return;
        }
        try {
            boolean removido = profRepo.excluir(profissional.getId());
            println(removido ? "Profissional excluído!" : "Nenhum registro removido.");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void manageServicos() {
        while (true) {
            menuHeader("SERVIÇOS");
            println("1. Cadastrar");
            println("2. Listar");
            println("3. Atualizar");
            println("4. Excluir");
            println("0. Voltar");
            switch (readInt("Opção: ")) {
                case 1:
                    cadastrarServico();
                    break;
                case 2:
                    listarServicos();
                    break;
                case 3:
                    atualizarServico();
                    break;
                case 4:
                    excluirServico();
                    break;
                case 0:
                    return;
                default:
                    println("Opção inválida.");
            }
            pause();
        }
    }

    private void cadastrarServico() {
        try {
            String desc = readString("Descrição: ");
            double valor = readDouble("Valor (R$): ");
            int tempoMedio = readInt("Tempo médio (min): ");

            Servico servico = new Servico(0, desc, valor, tempoMedio, 1);
            servRepo.incluir(servico);
            println("Serviço cadastrado!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void listarServicos() {
        try {
            servRepo.listarTodos().forEach(System.out::println);
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void atualizarServico() {
        Servico servico = escolherServico();
        if (servico == null) {
            println("Serviço não encontrado.");
            return;
        }
        try {
            String novaDesc = readString("Descrição (" + servico.getDescricao() + "): ");
            double novoValor = readDouble("Valor (" + servico.getValor() + "): ");
            int novoTempo = readInt("Tempo médio (" + servico.getTempoMedio() + "): ");
            int novoStatus = readInt("Status [" + servico.getStatus() + "]: ");

            if (!novaDesc.isEmpty())
                servico.setDescricao(novaDesc);
            servico.setValor(novoValor);
            servico.setTempoMedio(novoTempo);
            servico.setStatus(novoStatus);

            servRepo.alterar(servico);
            println("Serviço atualizado!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void excluirServico() {
        Servico servico = escolherServico();
        if (servico == null) {
            println("Serviço não encontrado.");
            return;
        }
        try {
            boolean removido = servRepo.excluir(servico.getId());
            println(removido ? "Serviço excluído!" : "Nenhum registro removido.");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void manageCatalogo() {
        while (true) {
            menuHeader("CATÁLOGO");
            println("1. Adicionar");
            println("2. Listar");
            println("3. Atualizar");
            println("4. Remover");
            println("0. Voltar");
            switch (readInt("Opção: ")) {
                case 1:
                    adicionarItemCatalogo();
                    break;
                case 2:
                    listarCatalogo();
                    break;
                case 3:
                    atualizarCatalogo();
                    break;
                case 4:
                    removerCatalogo();
                    break;
                case 0:
                    return;
                default:
                    println("Opção inválida.");
            }
            pause();
        }
    }

    private void adicionarItemCatalogo() {
        try {
            Profissional profissional = escolherProfissional();
            if (profissional == null)
                return;

            Servico servico = escolherServico();
            if (servico == null)
                return;

            Catalogo item = new Catalogo(profissional, servico, 1);
            catRepo.incluir(item);
            println("Item adicionado ao catálogo!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void listarCatalogo() {
        try {
            catRepo.listarTodos().forEach(System.out::println);
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void atualizarCatalogo() {
        try {
            listarCatalogo();
            int oldProf = readInt("Profissional (ID): ");
            int oldServ = readInt("Serviço       : ");
            Catalogo item = catRepo.buscarPorId(oldProf, oldServ);
            if (item == null) {
                println("Item não encontrado.");
                return;
            }

            println("Profissional atual: " + item.getProfissional().getNome());
            Profissional np = escolherProfissional();
            if (np == null)
                np = item.getProfissional();

            println("Serviço atual: " + item.getServico().getDescricao());
            Servico ns = escolherServico();
            if (ns == null)
                ns = item.getServico();

            int newStatus = readInt("Status [" + item.getStatus() + "]: ");

            if (np.getId() != oldProf || ns.getId() != oldServ) {
                catRepo.excluir(oldProf, oldServ);
                item = new Catalogo(np, ns, newStatus);
                catRepo.incluir(item);
                println("Item recriado com novos valores!");
            } else {
                item.setStatus(newStatus);
                catRepo.alterar(item);
                println("Status atualizado!");
            }
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void removerCatalogo() {
        try {
            listarCatalogo();
            int idProfissional = readInt("ID Profissional: ");
            int idServico = readInt("ID Serviço: ");
            boolean removido = catRepo.excluir(idProfissional, idServico);
            println(removido ? "Item removido!" : "Nenhum registro removido.");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void manageAgendas() {
        while (true) {
            menuHeader("AGENDAS");
            println("1. Criar");
            println("2. Listar");
            println("3. Excluir");
            println("4. Atualizar");
            println("5. Agendar serviço");
            println("6. Grade de disponibilidade");
            println("7. Disponibilidade profissional");
            println("8. Atualizar serviços agendados");
            println("0. Voltar");
            switch (readInt("Opção: ")) {
                case 1:
                    criarAgenda();
                    break;
                case 2:
                    listarAgendas();
                    break;
                case 3:
                    excluirAgenda();
                    break;
                case 4:
                    atualizarAgenda();
                    break;
                case 5:
                    agendarServico();
                    break;
                case 6:
                    gradeDisponibilidade();
                    break;
                case 7:
                    dispProfissional();
                    break;
                case 8:
                    editarServicosAgendados();
                    break;
                case 0:
                    return;
                default:
                    println("Opção inválida.");
            }
            pause();
        }
    }

    private void criarAgenda() {
        try {
            Cliente cliente = escolherCliente();
            if (cliente == null)
                return;

            println("1 = Hoje   2 = Amanhã");
            LocalDate data = readInt("Opção: ") == 2
                    ? LocalDate.now().plusDays(1)
                    : LocalDate.now();

            int hora = readInt("Hora (0–23): ");
            int minuto = readInt("Minuto: ");

            Agenda agenda = new Agenda(0, cliente, data.atTime(hora, minuto), 1);
            agRepo.incluir(agenda);
            println("Agenda criada!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void listarAgendas() {
        try {
            for (Agenda agenda : agRepo.listarTodos()) {
                println(agenda);
                List<ServicoAgendado> servAg = saRepo.listarPorAgenda(agenda.getId());
                if (!servAg.isEmpty()) {
                    println("  Serviços agendados:");
                    servAg.forEach(servicoAgendado -> println("    " + servicoAgendado));
                }
            }
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void excluirAgenda() {
        Agenda agenda = escolherAgenda();
        if (agenda == null) {
            println("Agenda não encontrada.");
            return;
        }
        try {
            boolean removido = agRepo.excluir(agenda.getId());
            println(removido ? "Agenda excluída!" : "Nenhum registro removido.");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void atualizarAgenda() {
        Agenda agenda = escolherAgenda();
        if (agenda == null) {
            println("Agenda não encontrada.");
            return;
        }
        try {
            println("1 = Hoje   2 = Amanhã");
            LocalDate data = readInt("Opção: ") == 2
                    ? LocalDate.now().plusDays(1)
                    : LocalDate.now();

            int hora = readInt("Hora (0–23): ");
            int minuto = readInt("Minuto: ");
            int status = readInt("Status [" + agenda.getStatus() + "]: ");

            agenda.setDataHora(data.atTime(hora, minuto));
            agenda.setStatus(status);
            agRepo.alterar(agenda);
            println("Agenda atualizada!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void agendarServico() {
        Agenda agenda = escolherAgenda();
        if (agenda == null) {
            println("Agenda não encontrada.");
            return;
        }

        Servico servico = escolherServico();
        if (servico == null) {
            println("Serviço não encontrado.");
            return;
        }

        LocalDateTime novoInicio = agenda.getDataHora();
        LocalDateTime novoFim = novoInicio.plusMinutes(servico.getTempoMedio());

        try {
            List<Profissional> disponiveis = new ArrayList<>();

            for (Profissional profissional : profRepo.listarTodos()) {
                Catalogo cat = catRepo.buscarPorId(profissional.getId(), servico.getId());
                if (cat == null || cat.getStatus() != 1) {
                    continue;
                }

                boolean conflito = false;
                for (Agenda outra : agRepo.listarTodos()) {
                    if (!outra.getDataHora().toLocalDate().equals(novoInicio.toLocalDate())) {
                        continue;
                    }
                    for (ServicoAgendado servicoAgendado : saRepo.listarPorAgenda(outra.getId())) {
                        if (servicoAgendado.getProfissional().getId() != profissional.getId()) {
                            continue;
                        }
                        if (outra.getId() == agenda.getId()) {
                            continue;
                        }

                        LocalDateTime iniExist = outra.getDataHora();
                        LocalDateTime fimExist = iniExist.plusMinutes(servicoAgendado.getServico().getTempoMedio());
                        if (novoInicio.isBefore(fimExist) && novoFim.isAfter(iniExist)) {
                            conflito = true;
                            break;
                        }
                    }
                    if (conflito)
                        break;
                }

                if (!conflito) {
                    disponiveis.add(profissional);
                }
            }

            if (disponiveis.isEmpty()) {
                println("Nenhum profissional disponível.");
                return;
            }

            println("Profissionais disponíveis:");
            disponiveis.forEach(p -> println(" [" + p.getId() + "] " + p.getNome()));

            int idEscolhido = readInt("Escolha ID: ");
            Profissional escolhido = profRepo.buscarPorId(idEscolhido);
            if (escolhido == null) {
                println("Profissional inválido.");
                return;
            }

            ServicoAgendado novoSA = new ServicoAgendado(agenda, servico, escolhido, 1);
            saRepo.incluir(novoSA);
            println("Serviço agendado!");
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void gradeDisponibilidade() {
        println("1 = Hoje   2 = Amanhã");
        LocalDate dia = readInt("Opção: ") == 2
                ? LocalDate.now().plusDays(1)
                : LocalDate.now();

        TreeSet<LocalDateTime> pontos = new TreeSet<>();
        pontos.add(dia.atTime(8, 0));
        pontos.add(dia.atTime(18, 0));

        class Evento {
            LocalDateTime inicio, fim;
            String profissional;

            Evento(LocalDateTime inicio, LocalDateTime fim, String profissional) {
                this.inicio = inicio;
                this.fim = fim;
                this.profissional = profissional;
            }
        }
        List<Evento> eventos = new ArrayList<>();

        try {
            for (Agenda agenda : agRepo.listarTodos()) {
                if (!agenda.getDataHora().toLocalDate().equals(dia))
                    continue;
                LocalDateTime cursor = agenda.getDataHora();
                for (ServicoAgendado servicoAgendado : saRepo.listarPorAgenda(agenda.getId())) {
                    LocalDateTime fim = cursor.plusMinutes(servicoAgendado.getServico().getTempoMedio());
                    eventos.add(new Evento(cursor, fim, servicoAgendado.getProfissional().getNome()));
                    pontos.add(cursor);
                    pontos.add(fim);
                    cursor = fim;
                }
            }

            List<LocalDateTime> cortes = new ArrayList<>(pontos);
            for (int i = 0; i < cortes.size() - 1; i++) {
                LocalDateTime inicio = cortes.get(i);
                LocalDateTime fim = cortes.get(i + 1);
                println(String.format("%5s - %5s", inicio.toLocalTime(), fim.toLocalTime()));

                Set<String> ocupados = new LinkedHashSet<>();
                for (Evento evento : eventos) {
                    if (evento.inicio.isBefore(fim) && evento.fim.isAfter(inicio)) {
                        ocupados.add(evento.profissional);
                    }
                }

                if (ocupados.isEmpty()) {
                    println("  [livre]");
                } else {
                    println("  " + String.join(" | ", ocupados));
                }
            }

        } catch (Exception e) {
            println("Erro ao gerar grade: " + e.getMessage());
        }
    }

    private void dispProfissional() {
        try {
            listarProfissionais();
            int idProfissional = readInt("ID do profissional: ");
            Profissional profissional = profRepo.buscarPorId(idProfissional);
            if (profissional == null) {
                println("Profissional não encontrado.");
                return;
            }
            println("1 = Hoje   2 = Amanhã");
            LocalDate data = readInt("Opção: ") == 2
                    ? LocalDate.now().plusDays(1)
                    : LocalDate.now();

            List<LocalDateTime[]> periods = new ArrayList<>();
            for (Agenda agenda : agRepo.listarTodos()) {
                if (agenda.getDataHora().toLocalDate().equals(data)) {
                    for (ServicoAgendado servicoAgendado : saRepo.listarPorAgenda(agenda.getId())) {
                        if (servicoAgendado.getProfissional().getId() == idProfissional) {
                            LocalDateTime oi = agenda.getDataHora();
                            LocalDateTime fi = oi.plusMinutes(servicoAgendado.getServico().getTempoMedio());
                            periods.add(new LocalDateTime[] { oi, fi });
                        }
                    }
                }
            }

            periods.sort(Comparator.comparing(iv -> iv[0]));
            println("Horários livres de " + profissional.getNome() + " em " + data + ":");
            LocalDateTime cursor = data.atTime(8, 0), end = data.atTime(18, 0);
            for (var iv : periods) {
                if (cursor.isBefore(iv[0])) {
                    println("-> " + cursor.toLocalTime() + " - " + iv[0].toLocalTime());
                }
                if (cursor.isBefore(iv[1])) {
                    cursor = iv[1];
                }
            }
            if (cursor.isBefore(end)) {
                println("-> " + cursor.toLocalTime() + " - " + end.toLocalTime());
            }
        } catch (Exception e) {
            println("Erro: " + e.getMessage());
        }
    }

    private void editarServicosAgendados() {
        try {
            Agenda agenda = escolherAgenda();
            if (agenda == null) {
                println("Agenda não encontrada.");
                return;
            }

            List<ServicoAgendado> servicosAgendados = saRepo.listarPorAgenda(agenda.getId());
            if (servicosAgendados.isEmpty()) {
                println("Nenhum serviço agendado para esta agenda.");
                return;
            }

            println("\n== Serviços Agendados ==");
            for (int i = 0; i < servicosAgendados.size(); i++) {
                ServicoAgendado servicoAgendado = servicosAgendados.get(i);
                println(" [" + (i + 1) + "] "
                        + servicoAgendado.getServico().getDescricao()
                        + "  |  " + servicoAgendado.getProfissional().getNome()
                        + "  |  Status: " + (servicoAgendado.isAtivo() ? "Ativo" : "Cancelado"));
            }

            int idx = readInt("Escolha o número do serviço (0 para voltar): ");
            if (idx == 0)
                return;
            if (idx < 1 || idx > servicosAgendados.size()) {
                println("Opção inválida.");
                return;
            }
            ServicoAgendado escolhido = servicosAgendados.get(idx - 1);

            println("Status atual: " + (escolhido.isAtivo() ? "1-Ativo" : "2-Cancelado"));
            int novoStatus = readInt("Novo status [1-Ativo,2-Cancelado]: ");
            if (novoStatus != 1 && novoStatus != 2) {
                println("Status inválido.");
                return;
            }
            escolhido.setStatus(novoStatus);

            boolean alterou = saRepo.alterar(escolhido);
            println(alterou ? "Status atualizado!" : "Falha ao atualizar status.");

        } catch (Exception e) {
            println("Erro ao editar serviço agendado: " + e.getMessage());
        }
    }

    private void menuHeader(String titulo) {
        clearScreen();
        println("-- " + titulo + " --");
    }

    private String readString(String msg) {
        System.out.print(msg);
        return input.nextLine().trim();
    }

    private int readInt(String msg) {
        System.out.print(msg);
        while (!input.hasNextInt())
            input.next();
        int v = input.nextInt();
        input.nextLine();
        return v;
    }

    private double readDouble(String msg) {
        System.out.print(msg);
        while (!input.hasNextDouble())
            input.next();
        double v = input.nextDouble();
        input.nextLine();
        return v;
    }

    private void pause() {
        System.out.print("\nPressione ENTER para continuar...");
        input.nextLine();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void println(Object o) {
        System.out.println(o);
    }
}
