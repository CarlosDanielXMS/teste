document.addEventListener('DOMContentLoaded', () => {
    const agendasModule = document.getElementById('agendas-module');
    let currentView = 'week';
    let currentDate = new Date();

    let sampleClients = [
        { id: 1, nome: 'Ana Silva' },
        { id: 2, nome: 'Bruno Costa' },
        {id: 3, nome: 'Fernando'}
    ];
    let sampleProfessionals = [
        { id: 1, nome: 'Felipe' },
        { id: 2, nome: 'Matheus' },
        { id: 3, nome: 'João' }
    ];
    let sampleServices = [
        { id: 101, descricao: 'Corte Cabelo', tempoMedio: 60, valor: 150.00 },
        { id: 102, descricao: 'Barba', tempoMedio: 30, valor: 80.00 },
        { id: 103, descricao: 'Pintura Cabelo', tempoMedio: 45, valor: 120.00 }
    ];

    let sampleAgendas = [
        { id: 1, clientId: 1, dataHora: new Date(2025, 6, 23, 9, 0), status: 1 },
        { id: 2, clientId: 2, dataHora: new Date(2025, 6, 23, 10, 30), status: 1 },
        { id: 3, clientId: 1, dataHora: new Date(2025, 6, 23, 14, 0), status: 1 },
        { id: 4, clientId: 2, dataHora: new Date(2025, 6, 23, 9, 0), status: 1 }
    ];
    let nextAgendaId = sampleAgendas.length > 0 ? Math.max(...sampleAgendas.map(a => a.id)) + 1 : 1;


    let sampleServicosAgendados = [
        { id: 1, agendaId: 1, servicoId: 101, profissionalId: 1, status: 1 },
        { id: 2, agendaId: 2, servicoId: 102, profissionalId: 2, status: 1 },
        { id: 3, agendaId: 3, servicoId: 103, profissionalId: 1, status: 0 },
        { id: 4, agendaId: 4, servicoId: 101, profissionalId: 3, status: 1 }
    ];
    let nextServicoAgendadoId = sampleServicosAgendados.length > 0 ? Math.max(...sampleServicosAgendados.map(sa => sa.id)) + 1 : 1;


    const getClientById = (id) => sampleClients.find(c => c.id === id);
    const getProfessionalById = (id) => sampleProfessionals.find(p => p.id === id);
    const getServiceById = (id) => sampleServices.find(s => s.id === id);


    function renderAgendasModule() {
        agendasModule.innerHTML = `
            <div class="module-header">
                <h2>Agendamentos</h2>
                <button class="btn btn-primary" id="add-agenda-btn">
                    <span class="material-icons">add</span> Nova Agenda
                </button>
            </div>
            <div class="calendar-toolbar">
                <div class="date-navigation">
                    <button class="btn-icon-only" id="prev-week-btn"><span class="material-icons">chevron_left</span></button>
                    <button class="btn btn-outline" id="today-btn">Hoje</button>
                    <button class="btn-icon-only" id="next-week-btn"><span class="material-icons">chevron_right</span></button>
                    <span id="current-period-display" class="current-period"></span>
                </div>
                <div class="view-switcher">
                    <button class="btn btn-outline active" id="week-view-btn">Semana</button>
                    <button class="btn btn-outline" id="day-view-btn">Dia</button>
                </div>
            </div>

            <div class="calendar-grid-container">
                <div class="time-column">
                    </div>
                <div class="calendar-main-grid" id="calendar-main-grid">
                    </div>
            </div>

            <div id="agendamento-modal" class="modal">
                <div class="modal-content">
                    <span class="close-button" onclick="hideModal('agendamento-modal')">&times;</span>
                    <h3>Agendar Serviço</h3>
                    <form id="agendamento-form">
                        <input type="hidden" id="agendamento-agenda-id">
                        <input type="hidden" id="agendamento-servico-agendado-id">

                        <div class="form-group">
                            <label for="agendamento-cliente">Cliente</label>
                            <select id="agendamento-cliente" required>
                                <option value="">Selecione um cliente</option>
                                ${sampleClients.map(c => `<option value="${c.id}">${c.nome}</option>`).join('')}
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="agendamento-data">Data</label>
                            <input type="date" id="agendamento-data" required>
                        </div>
                        <div class="form-group">
                            <label for="agendamento-hora">Hora</label>
                            <input type="time" id="agendamento-hora" required>
                        </div>

                        <div class="form-group">
                            <label for="agendamento-servico">Serviço</label>
                            <select id="agendamento-servico" required>
                                <option value="">Selecione um serviço</option>
                                ${sampleServices.map(s => `<option value="${s.id}">${s.descricao} (${s.tempoMedio} min)</option>`).join('')}
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="agendamento-profissional">Profissional Disponível</label>
                            <select id="agendamento-profissional">
                                <option value="">Selecione um profissional</option>
                                </select>
                        </div>

                        <div class="form-group">
                            <label for="agendamento-status">Status</label>
                            <select id="agendamento-status">
                                <option value="1">Ativo</option>
                                <option value="0">Cancelado</option>
                            </select>
                        </div>

                        <div class="form-actions">
                            <button type="button" class="btn btn-outline" onclick="hideModal('agendamento-modal')">Cancelar</button>
                            <button type="submit" class="btn btn-primary">Salvar Agendamento</button>
                        </div>
                    </form>
                </div>
            </div>
        `;

        document.getElementById('prev-week-btn').addEventListener('click', () => changePeriod(-1));
        document.getElementById('next-week-btn').addEventListener('click', () => changePeriod(1));
        document.getElementById('today-btn').addEventListener('click', () => { currentDate = new Date(); renderCalendar(); });
        document.getElementById('week-view-btn').addEventListener('click', () => { currentView = 'week'; updateViewButtons(); renderCalendar(); });
        document.getElementById('day-view-btn').addEventListener('click', () => { currentView = 'day'; updateViewButtons(); renderCalendar(); });
        document.getElementById('add-agenda-btn').addEventListener('click', openAddAppointmentModal);

        document.getElementById('agendamento-form').addEventListener('submit', handleAppointmentFormSubmit);
        document.getElementById('agendamento-servico').addEventListener('change', updateAvailableProfessionals);
        document.getElementById('agendamento-data').addEventListener('change', updateAvailableProfessionals);
        document.getElementById('agendamento-hora').addEventListener('change', updateAvailableProfessionals);

        renderCalendar();
    }

    function updateViewButtons() {
        document.getElementById('week-view-btn').classList.toggle('active', currentView === 'week');
        document.getElementById('day-view-btn').classList.toggle('active', currentView === 'day');
    }

    function renderCalendar() {
        const calendarMainGrid = document.getElementById('calendar-main-grid');
        const timeColumn = agendasModule.querySelector('.time-column');
        calendarMainGrid.innerHTML = '';
        timeColumn.innerHTML = '';

        const startHour = 8;
        const endHour = 18;
        const intervalMinutes = 30;

        for (let h = startHour; h < endHour; h++) {
            for (let m = 0; m < 60; m += intervalMinutes) {
                const timeDiv = document.createElement('div');
                timeDiv.classList.add('time-slot-label');
                timeDiv.textContent = `${String(h).padStart(2, '0')}:${String(m).padStart(2, '0')}`;
                timeColumn.appendChild(timeDiv);
            }
        }

        let startOfWeek = getStartOfWeek(currentDate);
        let endOfWeek = new Date(startOfWeek);
        endOfWeek.setDate(startOfWeek.getDate() + 6);

        let daysToDisplay = [];
        if (currentView === 'week') {
            document.getElementById('current-period-display').textContent =
                `${formatDate(startOfWeek)} - ${formatDate(endOfWeek)}`;
            for (let i = 0; i < 7; i++) {
                const day = new Date(startOfWeek);
                day.setDate(startOfWeek.getDate() + i);
                daysToDisplay.push(day);
            }
        } else {
            document.getElementById('current-period-display').textContent = formatDate(currentDate);
            daysToDisplay.push(currentDate);
        }

        if (currentView === 'week') {
            const cornerDiv = document.createElement('div');
            cornerDiv.classList.add('grid-header-corner');
            calendarMainGrid.appendChild(cornerDiv);

            daysToDisplay.forEach(day => {
                const header = document.createElement('div');
                header.classList.add('calendar-day-header');
                header.innerHTML = `
                    <span class="day-name">${getDayName(day)}</span>
                    <span class="day-date">${day.getDate()}</span>
                `;
                if (isSameDay(day, new Date())) {
                    header.classList.add('today');
                }
                calendarMainGrid.appendChild(header);
            });
            calendarMainGrid.style.gridTemplateColumns = `auto repeat(7, 1fr)`;
        } else {
             const cornerDiv = document.createElement('div');
             cornerDiv.classList.add('grid-header-corner');
             calendarMainGrid.appendChild(cornerDiv);

            sampleProfessionals.forEach(prof => {
                const header = document.createElement('div');
                header.classList.add('calendar-day-header');
                header.textContent = prof.nome;
                calendarMainGrid.appendChild(header);
            });
            calendarMainGrid.style.gridTemplateColumns = `auto repeat(${sampleProfessionals.length}, 1fr)`;
        }


        const gridCells = [];
        const numColumns = currentView === 'week' ? 7 : sampleProfessionals.length;
        const totalSlots = (endHour - startHour) * (60 / intervalMinutes);

        for (let i = 0; i < totalSlots; i++) {
            const time = new Date();
            time.setHours(startHour);
            time.setMinutes(i * intervalMinutes);

            for (let j = 0; j < numColumns; j++) {
                const cell = document.createElement('div');
                cell.classList.add('calendar-grid-cell');
                cell.dataset.hour = time.getHours();
                cell.dataset.minute = time.getMinutes();

                if (currentView === 'week') {
                    cell.dataset.date = daysToDisplay[j].toISOString().split('T')[0];
                    cell.addEventListener('click', (e) => openAddAppointmentModal(e, cell.dataset.date, cell.dataset.hour, cell.dataset.minute));
                } else {
                    cell.dataset.professionalId = sampleProfessionals[j].id;
                     cell.addEventListener('click', (e) => openAddAppointmentModal(e, currentDate.toISOString().split('T')[0], cell.dataset.hour, cell.dataset.minute, cell.dataset.professionalId));
                }

                gridCells.push(cell);
                calendarMainGrid.appendChild(cell);
            }
        }

        sampleServicosAgendados.forEach(sa => {
            const agenda = sampleAgendas.find(a => a.id === sa.agendaId);
            if (!agenda) return;

            const appointmentStart = new Date(agenda.dataHora);
            const appointmentService = getServiceById(sa.servicoId);
            const professional = getProfessionalById(sa.profissionalId);
            const client = getClientById(agenda.clientId);

            if (!appointmentService || !professional || !client) return;

            const appointmentEnd = new Date(appointmentStart.getTime() + appointmentService.tempoMedio * 60 * 1000);

            let isVisible = false;
            if (currentView === 'week') {
                const agendaDate = new Date(appointmentStart.getFullYear(), appointmentStart.getMonth(), appointmentStart.getDate());
                isVisible = daysToDisplay.some(d => isSameDay(d, agendaDate));
            } else {
                isVisible = isSameDay(appointmentStart, currentDate);
            }

            if (isVisible) {
                const apptDiv = document.createElement('div');
                apptDiv.classList.add('appointment-card');
                if (sa.status === 0) {
                    apptDiv.classList.add('cancelled');
                }
                apptDiv.innerHTML = `
                    <div class="appt-time">${formatTime(appointmentStart)} - ${formatTime(appointmentEnd)}</div>
                    <div class="appt-title">${appointmentService.descricao}</div>
                    <div class="appt-details">${client.nome} com ${professional.nome}</div>
                    <div class="appt-status">${sa.status === 1 ? 'Ativo' : 'Cancelado'}</div>
                `;
                apptDiv.onclick = (e) => openEditAppointmentModal(e, sa.id);

                const startCellIndex = getGridCellIndex(appointmentStart, startHour, intervalMinutes, daysToDisplay, professional, currentView);
                const durationCells = Math.ceil(appointmentService.tempoMedio / intervalMinutes);

                if (startCellIndex !== -1) {
                    const targetCell = gridCells[startCellIndex];
                    if (targetCell) {
                        const minutesIntoSlot = appointmentStart.getMinutes() % intervalMinutes;
                        const topOffset = (minutesIntoSlot / intervalMinutes) * 100;
                        apptDiv.style.top = `${topOffset}%`;
                        apptDiv.style.height = `${(appointmentService.tempoMedio / intervalMinutes) * 100}%`;

                        targetCell.appendChild(apptDiv);
                    }
                }
            }
        });
    }

    function getGridCellIndex(appointmentTime, startHour, intervalMinutes, daysToDisplay, professional, view) {
        const targetHour = appointmentTime.getHours();
        const targetMinute = appointmentTime.getMinutes();

        const totalMinutesFromStart = (targetHour - startHour) * 60 + targetMinute;
        const rowIndex = Math.floor(totalMinutesFromStart / intervalMinutes);

        if (view === 'week') {
            const targetDate = new Date(appointmentTime.getFullYear(), appointmentTime.getMonth(), appointmentTime.getDate());
            const colIndex = daysToDisplay.findIndex(day => isSameDay(day, targetDate));
            if (colIndex !== -1) {
                return rowIndex * daysToDisplay.length + colIndex;
            }
        } else {
            const colIndex = sampleProfessionals.findIndex(p => p.id === professional.id);
            if (colIndex !== -1) {
                return rowIndex * sampleProfessionals.length + colIndex;
            }
        }
        return -1;
    }


    function changePeriod(offset) {
        if (currentView === 'week') {
            currentDate.setDate(currentDate.getDate() + offset * 7);
        } else {
            currentDate.setDate(currentDate.getDate() + offset);
        }
        renderCalendar();
    }

    function formatDate(date) {
        return date.toLocaleDateString('pt-BR', { day: '2-digit', month: 'short', year: 'numeric' });
    }

    function formatTime(date) {
        return date.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
    }

    function getDayName(date) {
        return date.toLocaleDateString('pt-BR', { weekday: 'short' });
    }

    function getStartOfWeek(date) {
        const day = date.getDay();
        const diff = date.getDate() - day + (day === 0 ? -6 : 1);
        const startOfWeek = new Date(date.setDate(diff));
        startOfWeek.setHours(0, 0, 0, 0);
        return startOfWeek;
    }

    function isSameDay(d1, d2) {
        return d1.getFullYear() === d2.getFullYear() &&
               d1.getMonth() === d2.getMonth() &&
               d1.getDate() === d2.getDate();
    }

    function openAddAppointmentModal(event, dateStr = null, hour = null, minute = null, professionalId = null) {
        document.getElementById('agendamento-agenda-id').value = '';
        document.getElementById('agendamento-servico-agendado-id').value = '';
        document.getElementById('agendamento-cliente').value = '';
        document.getElementById('agendamento-servico').value = '';
        document.getElementById('agendamento-profissional').innerHTML = '<option value="">Selecione um profissional</option>';
        document.getElementById('agendamento-status').value = '1';

        const today = new Date();
        const dateInput = document.getElementById('agendamento-data');
        const timeInput = document.getElementById('agendamento-hora');

        if (dateStr) {
            dateInput.value = dateStr;
        } else {
            dateInput.valueAsDate = today;
        }

        if (hour !== null && minute !== null) {
            timeInput.value = `${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
        } else {
            timeInput.value = '';
        }

        if (professionalId) {
        }

        updateAvailableProfessionals();

        showModal('agendamento-modal');
    }

    function openEditAppointmentModal(event, servicoAgendadoId) {
        event.stopPropagation();

        const sa = sampleServicosAgendados.find(s => s.id === servicoAgendadoId);
        const agenda = sampleAgendas.find(a => a.id === sa.agendaId);

        if (!sa || !agenda) {
            alert('Agendamento não encontrado.');
            return;
        }

        document.getElementById('agendamento-agenda-id').value = agenda.id;
        document.getElementById('agendamento-servico-agendado-id').value = sa.id;
        document.getElementById('agendamento-cliente').value = agenda.clientId;
        document.getElementById('agendamento-servico').value = sa.servicoId;

        const apptDate = new Date(agenda.dataHora);
        document.getElementById('agendamento-data').valueAsDate = apptDate;
        document.getElementById('agendamento-hora').value = formatTime(apptDate); // HH:MM

        document.getElementById('agendamento-status').value = sa.status;

        // Populate professionals after setting service, date, time
        updateAvailableProfessionals().then(() => {
            document.getElementById('agendamento-profissional').value = sa.profissionalId;
        });

        showModal('agendamento-modal');
    }

    async function updateAvailableProfessionals() {
        const serviceId = document.getElementById('agendamento-servico').value;
        const dateInput = document.getElementById('agendamento-data').value;
        const timeInput = document.getElementById('agendamento-hora').value;
        const professionalSelect = document.getElementById('agendamento-profissional');
        professionalSelect.innerHTML = '<option value="">Selecione um profissional</option>'; // Clear

        if (!serviceId || !dateInput || !timeInput) {
            return;
        }

        const selectedService = getServiceById(parseInt(serviceId));
        if (!selectedService) return;

        const [hour, minute] = timeInput.split(':').map(Number);
        const selectedDateTime = new Date(`${dateInput}T${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}:00`);
        const appointmentEnd = new Date(selectedDateTime.getTime() + selectedService.tempoMedio * 60 * 1000);

        const availableProf = [];

        // Simulating catalog lookup (professional offers service)
        // For simplicity, assume all sampleProfessionals offer all sampleServices in this frontend demo
        const professionalsOfferingService = sampleProfessionals.filter(prof => true); // In real app: check catRepo

        for (const prof of professionalsOfferingService) {
            let isConflicting = false;
            for (const existingSa of sampleServicosAgendados) {
                const existingAgenda = sampleAgendas.find(a => a.id === existingSa.agendaId);
                if (!existingAgenda || existingSa.profissionalId !== prof.id || existingSa.status === 0) { // Skip cancelled appts
                    continue;
                }

                const existingService = getServiceById(existingSa.servicoId);
                if (!existingService) continue;

                const existingApptStart = new Date(existingAgenda.dataHora);
                const existingApptEnd = new Date(existingApptStart.getTime() + existingService.tempoMedio * 60 * 1000);

                // Check for overlap on the same day
                if (isSameDay(selectedDateTime, existingApptStart) &&
                    selectedDateTime < existingApptEnd && appointmentEnd > existingApptStart) {
                    isConflicting = true;
                    break;
                }
            }

            if (!isConflicting) {
                availableProf.push(prof);
            }
        }

        if (availableProf.length === 0) {
            professionalSelect.innerHTML = '<option value="">Nenhum profissional disponível</option>';
            professionalSelect.disabled = true;
        } else {
            professionalSelect.disabled = false;
            availableProf.forEach(prof => {
                const option = document.createElement('option');
                option.value = prof.id;
                option.textContent = prof.nome;
                professionalSelect.appendChild(option);
            });
        }
    }


    function handleAppointmentFormSubmit(event) {
        event.preventDefault();

        const agendaId = document.getElementById('agendamento-agenda-id').value;
        const servicoAgendadoId = document.getElementById('agendamento-servico-agendado-id').value;
        const clientId = parseInt(document.getElementById('agendamento-cliente').value);
        const dateInput = document.getElementById('agendamento-data').value;
        const timeInput = document.getElementById('agendamento-hora').value;
        const serviceId = parseInt(document.getElementById('agendamento-servico').value);
        const professionalId = parseInt(document.getElementById('agendamento-profissional').value);
        const status = parseInt(document.getElementById('agendamento-status').value);

        if (!clientId || !dateInput || !timeInput || !serviceId || !professionalId) {
            alert('Por favor, preencha todos os campos obrigatórios.');
            return;
        }

        const [hour, minute] = timeInput.split(':').map(Number);
        const dataHora = new Date(`${dateInput}T${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}:00`);

        if (servicoAgendadoId) {
            // Edit existing ServicoAgendado
            const saIndex = sampleServicosAgendados.findIndex(s => s.id === parseInt(servicoAgendadoId));
            if (saIndex !== -1) {
                const originalAgenda = sampleAgendas.find(a => a.id === sampleServicosAgendados[saIndex].agendaId);
                // Update Agenda if date/time/client changed (simplified)
                if (originalAgenda.clientId !== clientId || !isSameDay(originalAgenda.dataHora, dataHora) || formatTime(originalAgenda.dataHora) !== formatTime(dataHora)) {
                    originalAgenda.clientId = clientId;
                    originalAgenda.dataHora = dataHora;
                }
                sampleServicosAgendados[saIndex].servicoId = serviceId;
                sampleServicosAgendados[saIndex].profissionalId = professionalId;
                sampleServicosAgendados[saIndex].status = status;
                alert('Agendamento atualizado com sucesso!');
            }
        } else {
            // Create new Agenda and ServicoAgendado
            const newAgenda = {
                id: nextAgendaId++,
                clientId: clientId,
                dataHora: dataHora,
                status: 1 // Default status for new agenda
            };
            sampleAgendas.push(newAgenda);

            const newServicoAgendado = {
                id: nextServicoAgendadoId++,
                agendaId: newAgenda.id,
                servicoId: serviceId,
                profissionalId: professionalId,
                status: status
            };
            sampleServicosAgendados.push(newServicoAgendado);
            alert('Agendamento criado com sucesso!');
        }

        hideModal('agendamento-modal');
        renderCalendar(); // Re-render calendar to show changes
    }

    // Export functions to global scope for HTML event handlers
    window.openEditAppointmentModal = openEditAppointmentModal;
    window.updateAvailableProfessionals = updateAvailableProfessionals;


    // Quando o módulo de agendas for ativado, renderiza-o
    document.querySelector('.sidebar-item[data-module="agendas"]').addEventListener('click', renderAgendasModule);

    // Initial render if agendas module is active on load (e.g., if it's the default view)
    if (agendasModule.classList.contains('active')) {
        renderAgendasModule();
    }
});