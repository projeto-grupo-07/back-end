package school.sptech.crud_proj_v1.dto.RabbitMQ;

public record EmailMessage(
        String destinatario,
        String assunto,
        String corpo
) {}
