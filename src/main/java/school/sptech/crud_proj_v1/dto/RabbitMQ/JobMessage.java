package school.sptech.crud_proj_v1.dto.RabbitMQ;

import java.io.Serializable;

public record JobMessage(
        String jobId,
        String fileKey
) implements Serializable {}
