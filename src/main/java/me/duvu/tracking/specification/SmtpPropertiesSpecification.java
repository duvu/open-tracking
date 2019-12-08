package me.duvu.tracking.specification;

import me.duvu.tracking.domain.SmtpProperties;
import me.duvu.tracking.domain.SmtpProperties_;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author beou on 12/7/19 23:52
 */
public class SmtpPropertiesSpecification {
    public static Specification<SmtpProperties> searchByAccountId(Long accountId) {
        return (Specification<SmtpProperties>) (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get(SmtpProperties_.accountId), accountId);
        };
    }
}
