package com.joaod.DLRConsultoria.repository;

import com.joaod.DLRConsultoria.entity.EmailEntity;
import com.joaod.DLRConsultoria.enums.SituacaoEmailEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailRepository extends JpaRepository<EmailEntity, Integer> {

    public List<EmailRepository> findByDestinatarioAndSituacaoEmail(String destinatario, SituacaoEmailEnum situacaoEmail);

    public List<EmailRepository> findByDestinatario(String destinatario);
}
