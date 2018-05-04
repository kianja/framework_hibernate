CREATE DATABASE restaurant;

CREATE TABLE plat (
	id	 INT AUTO_INCREMENT PRIMARY KEY,
	nom	 VARCHAR(20)
);
create table categorie(
   id int AUTO_INCREMENT PRIMARY key,
   nom varchar(20)
   );
create table utilisateur (
	id int AUTO_INCREMENT primary key,
	nom varchar(20),
	prenom varchar(20)
);
create table commande(
	id int AUTO_INCREMENT primary key,
	idUtilisateur int ,
	daty varchar(20),
	foreign key (idUtilisateur) references utilisateur(id))
