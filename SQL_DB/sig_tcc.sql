-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1
-- PostgreSQL version: 9.6
-- Project Site: pgmodeler.io
-- Model Author: ---


-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: sig_tcc | type: DATABASE --
-- -- DROP DATABASE IF EXISTS sig_tcc;
-- CREATE DATABASE sig_tcc;
-- -- ddl-end --
-- 

-- object: public.alunos | type: TABLE --
-- DROP TABLE IF EXISTS public.alunos CASCADE;
CREATE TABLE public.alunos(
	id serial NOT NULL,
	ra text NOT NULL,
	nome text NOT NULL,
	email text NOT NULL,
	telefone text NOT NULL,
	id_acesso integer,
	id_curso integer,
	CONSTRAINT alunos_pk PRIMARY KEY (id)

);
-- ddl-end --
COMMENT ON COLUMN public.alunos.id IS 'user id - primary key';
-- ddl-end --
COMMENT ON COLUMN public.alunos.nome IS 'nome do aluno, professor ou coordenador';
-- ddl-end --
COMMENT ON COLUMN public.alunos.email IS 'email do usuario';
-- ddl-end --
COMMENT ON COLUMN public.alunos.telefone IS 'telefone do usuario';
-- ddl-end --
ALTER TABLE public.alunos OWNER TO postgres;
-- ddl-end --

-- object: public.curso | type: TABLE --
-- DROP TABLE IF EXISTS public.curso CASCADE;
CREATE TABLE public.curso(
	id serial NOT NULL,
	descricao text NOT NULL,
	departamento text,
	turma text,
	CONSTRAINT curso_pk PRIMARY KEY (id)

);
-- ddl-end --
COMMENT ON COLUMN public.curso.descricao IS 'nome do curso';
-- ddl-end --
COMMENT ON COLUMN public.curso.departamento IS 'departamento do curso';
-- ddl-end --
COMMENT ON COLUMN public.curso.turma IS 'turma pertencente ao inicio da orientacao';
-- ddl-end --
ALTER TABLE public.curso OWNER TO postgres;
-- ddl-end --

-- object: public.acesso | type: TABLE --
-- DROP TABLE IF EXISTS public.acesso CASCADE;
CREATE TABLE public.acesso(
	id serial NOT NULL,
	"user" text NOT NULL,
	pass text NOT NULL,
	CONSTRAINT acesso_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.acesso OWNER TO postgres;
-- ddl-end --

-- object: public.tipotcc | type: TABLE --
-- DROP TABLE IF EXISTS public.tipotcc CASCADE;
CREATE TABLE public.tipotcc(
	id serial NOT NULL,
	descricao text NOT NULL,
	CONSTRAINT tipotcc_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.tipotcc OWNER TO postgres;
-- ddl-end --

-- object: public.tcc | type: TABLE --
-- DROP TABLE IF EXISTS public.tcc CASCADE;
CREATE TABLE public.tcc(
	id serial NOT NULL,
	tema text NOT NULL,
	projeto text NOT NULL,
	dt_inicio date NOT NULL,
	dt_termino date,
	dt_defesa date,
	id_professores integer,
	id_tipotcc integer,
	id_alunos integer,
	CONSTRAINT tcc_pk PRIMARY KEY (id)

);
-- ddl-end --
COMMENT ON COLUMN public.tcc.dt_inicio IS 'data do inicio da orientacao';
-- ddl-end --
COMMENT ON COLUMN public.tcc.dt_termino IS 'data da previsao de termino';
-- ddl-end --
COMMENT ON COLUMN public.tcc.dt_defesa IS 'data da apresentacao do projeto';
-- ddl-end --
ALTER TABLE public.tcc OWNER TO postgres;
-- ddl-end --

-- object: public.professores | type: TABLE --
-- DROP TABLE IF EXISTS public.professores CASCADE;
CREATE TABLE public.professores(
	id serial NOT NULL,
	registro integer NOT NULL,
	nome text NOT NULL,
	email text NOT NULL,
	telefone text NOT NULL,
	area text,
	coordenador boolean,
	id_acesso integer,
	CONSTRAINT professores_pk PRIMARY KEY (id)

);
-- ddl-end --
COMMENT ON COLUMN public.professores.id IS 'user id - primary key';
-- ddl-end --
COMMENT ON COLUMN public.professores.nome IS 'nome do aluno, professor ou coordenador';
-- ddl-end --
COMMENT ON COLUMN public.professores.email IS 'email do usuario';
-- ddl-end --
COMMENT ON COLUMN public.professores.telefone IS 'telefone do usuario';
-- ddl-end --
ALTER TABLE public.professores OWNER TO postgres;
-- ddl-end --

-- object: acesso_fk | type: CONSTRAINT --
-- ALTER TABLE public.alunos DROP CONSTRAINT IF EXISTS acesso_fk CASCADE;
ALTER TABLE public.alunos ADD CONSTRAINT acesso_fk FOREIGN KEY (id_acesso)
REFERENCES public.acesso (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: alunos_uq | type: CONSTRAINT --
-- ALTER TABLE public.alunos DROP CONSTRAINT IF EXISTS alunos_uq CASCADE;
ALTER TABLE public.alunos ADD CONSTRAINT alunos_uq UNIQUE (id_acesso);
-- ddl-end --

-- object: acesso_fk | type: CONSTRAINT --
-- ALTER TABLE public.professores DROP CONSTRAINT IF EXISTS acesso_fk CASCADE;
ALTER TABLE public.professores ADD CONSTRAINT acesso_fk FOREIGN KEY (id_acesso)
REFERENCES public.acesso (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: professores_uq | type: CONSTRAINT --
-- ALTER TABLE public.professores DROP CONSTRAINT IF EXISTS professores_uq CASCADE;
ALTER TABLE public.professores ADD CONSTRAINT professores_uq UNIQUE (id_acesso);
-- ddl-end --

-- object: curso_fk | type: CONSTRAINT --
-- ALTER TABLE public.alunos DROP CONSTRAINT IF EXISTS curso_fk CASCADE;
ALTER TABLE public.alunos ADD CONSTRAINT curso_fk FOREIGN KEY (id_curso)
REFERENCES public.curso (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: alunos_uq1 | type: CONSTRAINT --
-- ALTER TABLE public.alunos DROP CONSTRAINT IF EXISTS alunos_uq1 CASCADE;
ALTER TABLE public.alunos ADD CONSTRAINT alunos_uq1 UNIQUE (id_curso);
-- ddl-end --

-- object: tipotcc_fk | type: CONSTRAINT --
-- ALTER TABLE public.tcc DROP CONSTRAINT IF EXISTS tipotcc_fk CASCADE;
ALTER TABLE public.tcc ADD CONSTRAINT tipotcc_fk FOREIGN KEY (id_tipotcc)
REFERENCES public.tipotcc (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: tcc_uq | type: CONSTRAINT --
-- ALTER TABLE public.tcc DROP CONSTRAINT IF EXISTS tcc_uq CASCADE;
ALTER TABLE public.tcc ADD CONSTRAINT tcc_uq UNIQUE (id_tipotcc);
-- ddl-end --

-- object: alunos_fk | type: CONSTRAINT --
-- ALTER TABLE public.tcc DROP CONSTRAINT IF EXISTS alunos_fk CASCADE;
ALTER TABLE public.tcc ADD CONSTRAINT alunos_fk FOREIGN KEY (id_alunos)
REFERENCES public.alunos (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: tcc_uq1 | type: CONSTRAINT --
-- ALTER TABLE public.tcc DROP CONSTRAINT IF EXISTS tcc_uq1 CASCADE;
ALTER TABLE public.tcc ADD CONSTRAINT tcc_uq1 UNIQUE (id_alunos);
-- ddl-end --

-- object: professores_fk | type: CONSTRAINT --
-- ALTER TABLE public.tcc DROP CONSTRAINT IF EXISTS professores_fk CASCADE;
ALTER TABLE public.tcc ADD CONSTRAINT professores_fk FOREIGN KEY (id_professores)
REFERENCES public.professores (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: public.acompanhamento | type: TABLE --
-- DROP TABLE IF EXISTS public.acompanhamento CASCADE;
CREATE TABLE public.acompanhamento(
	id serial NOT NULL,
	data date NOT NULL,
	observacoes text NOT NULL,
	id_tcc integer,
	CONSTRAINT acompanhamento_id PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.acompanhamento OWNER TO postgres;
-- ddl-end --

-- object: tcc_fk | type: CONSTRAINT --
-- ALTER TABLE public.acompanhamento DROP CONSTRAINT IF EXISTS tcc_fk CASCADE;
ALTER TABLE public.acompanhamento ADD CONSTRAINT tcc_fk FOREIGN KEY (id_tcc)
REFERENCES public.tcc (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


