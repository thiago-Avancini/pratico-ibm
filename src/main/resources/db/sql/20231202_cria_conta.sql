CREATE TABLE IF NOT EXISTS `conta`
(
  `id` varchar(255) NOT NULL,
  `nome_cliente` varchar(255) NOT NULL,
  `cpf` varchar(255) NOT NULL,
  `numero_conta` varchar(20) NOT NULL,
  `saldo_atual` double NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_CPF` (`cpf`),
  UNIQUE KEY `UK_NUMERO_CONTA` (`numero_conta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
