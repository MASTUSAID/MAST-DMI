package com.rmsi.mast.studio.dao;

import com.rmsi.mast.studio.domain.Dispute;
import java.util.List;

public interface DisputeDao extends GenericDAO<Dispute, Long> {
    List<Dispute> findByPropId(Long usin);
    List<Dispute> findActiveByPropId(Long usin);
    Dispute save(Dispute dispute);
}
