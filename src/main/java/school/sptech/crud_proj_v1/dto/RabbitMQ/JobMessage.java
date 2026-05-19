package school.sptech.crud_proj_v1.dto.RabbitMQ;

import java.io.Serializable;

public record JobMessage(
        Integer ano,
        String mes,
        String jobId,
        String fileKey
) implements Serializable {}
