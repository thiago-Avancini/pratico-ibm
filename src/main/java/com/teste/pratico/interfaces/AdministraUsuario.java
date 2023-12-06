package com.teste.pratico.interfaces;

import com.teste.pratico.model.UsuarioRequest;
import com.teste.pratico.model.UsuarioResponse;

public interface AdministraUsuario {

  UsuarioResponse registrarNovoUsuario(UsuarioRequest request);
}
