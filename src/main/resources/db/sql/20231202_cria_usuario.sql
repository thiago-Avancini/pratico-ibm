CREATE TABLE IF NOT EXISTS `usuario`
(
  `id` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `sobrenome` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USUARIO` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
