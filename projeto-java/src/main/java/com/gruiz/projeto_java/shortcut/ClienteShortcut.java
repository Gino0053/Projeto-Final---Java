package com.gruiz.projeto_java.shortcut;

import com.gruiz.projeto_java.entity.Cliente;
import com.gruiz.projeto_java.exception.CheckCorrentistaException;
import com.gruiz.projeto_java.exception.SaldoNegativoException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteShortcut {
	
	private final static float CREDITO_BASE = 200F;
	
	public static Cliente verificaCliente(Cliente cliente) {
		if (!cliente.getCorrentista() && cliente.getSaldoCc() != null) {
	        throw new CheckCorrentistaException("Impossível salvar saldo de conta corrente se o cliente não é correntista.");
	    }
		
        if (cliente.getSaldoCc() != null && cliente.getSaldoCc() < 0) {
            throw new SaldoNegativoException("O saldo da conta corrente não pode ser negativo, pois não faz sentido cliente abrir conta devendo.");
        }
        
		if (!cliente.getCorrentista()) {
			cliente.setSaldoCc(null);
			cliente.setScoreCredito(CREDITO_BASE);
		}else if(cliente.getCorrentista() && cliente.getSaldoCc() <= 2000) {
			cliente.setScoreCredito(CREDITO_BASE);
		} else if(cliente.getSaldoCc() > 2000) {
			cliente.setScoreCredito((float) (cliente.getSaldoCc() * 0.1));
		}
		return cliente;
	}
	
	
}