package com.box.l10n.mojito.service.branch.notification.job;

import com.box.l10n.mojito.quartz.QuartzPollableJob;
import com.box.l10n.mojito.service.branch.notification.BranchNotificationService;
import com.box.l10n.mojito.service.branch.notificationlegacy.BranchNotificationServiceLegacy;
import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Send the screenshot missing notification for a branch if needed.
 *
 * @author jaurambault
 */
@Component
@DisallowConcurrentExecution
public class BranchNotificationMissingScreenshotsJob
    extends QuartzPollableJob<BranchNotificationMissingScreenshotsJobInput, Void> {

  /** logger */
  static Logger logger = LoggerFactory.getLogger(BranchNotificationMissingScreenshotsJob.class);

  @Autowired BranchNotificationServiceLegacy branchNotificationServiceLegacy;

  @Autowired BranchNotificationService branchNotificationService;

  @Override
  public Void call(BranchNotificationMissingScreenshotsJobInput input) throws Exception {
    if (input.getSenderType() == null) {
      logger.debug(
          "execute for branchId: {} and notifier id: {}",
          input.getBranchId(),
          input.getNotifierId());
      branchNotificationService.sendMissingScreenshotNotificationForBranch(
          input.getBranchId(), input.getNotifierId());
    } else {
      logger.debug(
          "execute for branchId: {} and sender type: {}",
          input.getBranchId(),
          input.getSenderType());
      branchNotificationServiceLegacy.sendMissingScreenshotNotificationForBranch(
          input.getBranchId(), input.getSenderType());
    }
    return null;
  }
}
