ALTER TABLE public.rede_apoio ADD enabled boolean NOT NULL;

ALTER TABLE auditing.rede_apoio_audited ADD enabled boolean ;

insert into public.rede_apoio values(1, now(), now(), 'CREA', '123133', 1, 1, true);