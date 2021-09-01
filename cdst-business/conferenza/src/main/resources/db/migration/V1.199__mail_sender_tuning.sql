CREATE TABLE mail_hashes (
	id serial NOT NULL,
	data BYTEA NOT NULL
);
insert into mail_hashes (id,data) values (1, '');