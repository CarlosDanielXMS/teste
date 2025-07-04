CREATE DATABASE ProjetoInter

CREATE TABLE Cliente (
	id			INT				NOT NULL IDENTITY,
	nome		VARCHAR(50)		NOT NULL,
	telefone	VARCHAR(11)		NOT NULL,
	email		VARCHAR(30),
	senha		VARCHAR(20),
	status		SMALLINT		NOT NULL,

	PRIMARY KEY (id),
	UNIQUE(telefone, email),
	CHECK (LEN(senha) > 8),
	CHECK (status IN (1, 2))
)

CREATE TABLE Profissional (
	id				INT				NOT NULL IDENTITY,
	nome			VARCHAR(50)		NOT NULL,
	telefone		VARCHAR(11)		NOT NULL,
	email			VARCHAR(30),
	salarioFixo		SMALLMONEY		NOT NULL,
	comissao		DECIMAL(10,2)	NOT NULL,
	senha			VARCHAR(20),
	status			SMALLINT		NOT NULL,

	PRIMARY KEY (id),
	UNIQUE(telefone, email),
	CHECK (salarioFixo > 0),
	CHECK (comissao >= 0),
	CHECK (LEN(senha) > 8),
	CHECK (status IN (1, 2))
)

CREATE TABLE Servico (
	id			INT				NOT NULL IDENTITY,
	descricao	VARCHAR(50)		NOT NULL,
	valor		SMALLMONEY		NOT NULL,
	status		SMALLINT		NOT NULL,

	PRIMARY KEY (id),
	UNIQUE(descricao),
	CHECK (valor > 0),
	CHECK (status IN (1, 2))
)

CREATE TABLE Agenda (
	id			INT			NOT NULL IDENTITY,
	cliente		INT			NOT NULL,
	dataHora	DATE		NOT NULL,
	status		SMALLINT	NOT NULL,

	PRIMARY KEY (id),
	FOREIGN KEY (cliente) REFERENCES Cliente,
	CHECK (status IN (1, 2))
)

CREATE TABLE Servico_Agendado (
	idAgenda			INT		NOT NULL,
	idServico			INT		NOT NULL,
	idProfissional		INT		NOT NULL,

	PRIMARY KEY (idAgenda, idServico, idProfissional),
	FOREIGN KEY (idAgenda) REFERENCES Agenda,
	FOREIGN KEY (idServico) REFERENCES Servico,
	FOREIGN KEY (idProfissional) REFERENCES Profissional,
)

CREATE TABLE Servico_por_Profissional (
	idProfissional		INT		NOT NULL,
	idServico			INT		NOT NULL,

	FOREIGN KEY (idServico) REFERENCES Servico,
	FOREIGN KEY (idProfissional) REFERENCES Profissional,
)