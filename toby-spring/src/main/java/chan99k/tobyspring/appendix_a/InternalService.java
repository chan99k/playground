
package chan99k.tobyspring.appendix_a;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InternalService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void internalRequiresNew() {
        log.info(">>>>> InternalService.internalRequiresNew 시작 <<<<<");
        log.info("InternalService.internalRequiresNew 트랜잭션: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info(">>>>> InternalService.internalRequiresNew 종료 <<<<<");
    }
}
