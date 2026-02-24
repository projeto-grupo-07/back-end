package school.sptech.crud_proj_v1.service;

import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.entity.TentativaLogin;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TentativaLoginService {

    private final ConcurrentHashMap<String, TentativaLogin> tentativas = new ConcurrentHashMap<>();

    private static final int LIMITE_TENTATIVAS = 5;
    private static final int TEMPO_BLOQUEIO_MINUTOS = 5;

    public TentativaLogin getByEmail(String email) {
        return tentativas.getOrDefault(email, new TentativaLogin(email));
    }

    public void registrarFalha(String email) {
        TentativaLogin tentativa = getByEmail(email);
        tentativa.incrementarFalha(LIMITE_TENTATIVAS, TEMPO_BLOQUEIO_MINUTOS);
        tentativas.put(email, tentativa);
    }

    public void resetar(String email) {
        TentativaLogin tentativa = getByEmail(email);
        tentativa.resetar();
        tentativas.put(email, tentativa);
    }
}
