package school.sptech.crud_proj_v1.entity;

import java.time.LocalDateTime;

public class TentativaLogin {
    private final String email;
    private int falhas;
    private LocalDateTime desbloqueio;

    public TentativaLogin(String email) {
        this.email = email;
        this.falhas = 0;
        this.desbloqueio = null;
    }

    public void incrementarFalha(int limite, int minutosBloqueio) {
        falhas++;
        if (falhas >= limite) {
            desbloqueio = LocalDateTime.now().plusMinutes(minutosBloqueio);
        }
    }

    public void resetar() {
        falhas = 0;
        desbloqueio = null;
    }

    public boolean isBloqueado() {
        return desbloqueio != null && LocalDateTime.now().isBefore(desbloqueio);
    }

    public int getFalhas() {
        return falhas;
    }

    public LocalDateTime getDesbloqueio() {
        return desbloqueio;
    }
}
