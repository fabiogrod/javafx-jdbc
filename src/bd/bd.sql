CREATE TABLE departamento (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Nome varchar(60) DEFAULT NULL,
  PRIMARY KEY (Id)
);

CREATE TABLE vendedor (
  Id int(11) NOT NULL AUTO_INCREMENT,
  Nome varchar(60) NOT NULL,
  Email varchar(100) NOT NULL,
  DataAniversario datetime NOT NULL,
  SalarioBase double NOT NULL,
  IdDepartamento int(11) NOT NULL,
  PRIMARY KEY (Id),
  FOREIGN KEY (IdDepartamento) REFERENCES departamento (Id)
);

INSERT INTO departamento (Nome) VALUES 
  ('Computadores'),
  ('Eletrônicos'),
  ('Moda'),
  ('Livros');

INSERT INTO vendedor (Nome, Email, DataAniversario, SalarioBase, IdDepartamento) VALUES 
  ('Pedro Silva','pedro@gmail.com','1998-04-21 00:00:00',1000,1),
  ('Paula Lopes','paula@gmail.com','1979-12-31 00:00:00',3500,2),
  ('Fabio Rodrigues','fabio@gmail.com','1988-01-15 00:00:00',2200,1),
  ('Felipe Pinheiro','felipe@gmail.com','1993-11-30 00:00:00',3000,4),
  ('Thais Aparecida','thais@gmail.com','2000-01-09 00:00:00',4000,3),
  ('Carlos Franca','carlos@gmail.com','1997-03-04 00:00:00',3000,2);