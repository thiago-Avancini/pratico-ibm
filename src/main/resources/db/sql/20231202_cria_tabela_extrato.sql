CREATE TABLE IF NOT EXISTS `extrato`
(
  `id` varchar(255) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `tipo_movimentacao` varchar(255) NOT NULL,
  `valor` double NOT NULL,
  `data_operacao` date NOT NULL,
  `conta_id` varchar(255) NOT NULL,
  `numero_conta_origem` varchar(255),
  `numero_conta_destino` varchar(255),
  PRIMARY KEY (`id`),
  KEY `FK_EXTRATO_CONTA_ID` (`conta_id`),
    CONSTRAINT `FK_EXTRATO_CONTA_ID` FOREIGN KEY (`conta_id`) REFERENCES `conta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
