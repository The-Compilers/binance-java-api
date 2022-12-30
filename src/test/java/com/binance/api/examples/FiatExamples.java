package com.binance.api.examples;

import com.binance.api.client.constant.TimeRange;
import com.binance.api.client.domain.fiat.FiatPaymentType;

/**
 * Examples of Fiat endpoints.
 */
public class FiatExamples extends AuthenticatedExampleBase {
  public static void main(String[] args) {
    FiatExamples examples = new FiatExamples();
    examples.run();
  }

  private void run() {
    printFiatDepositHistory();
    printFiatWithdrawHistory();
    printFiatPaymentHistory();
  }

  private void printFiatPaymentHistory() {
    printTransactions("2022-12", true, true);
  }

  private void printFiatDepositHistory() {
    printDeposits("2022-12");
  }

  private void printFiatWithdrawHistory() {
    printWithdrawals("2020-10");
  }

  private void printDeposits(String month) {
    printTransactions(month, true, false);
  }

  private void printWithdrawals(String month) {
    printTransactions(month, false, false);
  }

  private void printTransactions(String month, boolean isDeposit, boolean isPayment) {
    String transactionType = getTransactionType(isDeposit, isPayment);
    String title = "Fiat " + transactionType + " for " + month;
    System.out.println(title);
    TimeRange range = TimeRange.createForMonth(month);
    Object history = switch (transactionType) {
      case "buy-payments" -> client.getFiatPaymentHistory(FiatPaymentType.BUY,
          range.getStartTime(), range.getEndTime());
      case "sell-payments" -> client.getFiatPaymentHistory(FiatPaymentType.SELL,
          range.getStartTime(), range.getEndTime());
      case "deposits" -> client.getFiatDepositHistory(range.getStartTime(), range.getEndTime());
      case "withdrawals" -> client.getFiatWithdrawHistory(range.getStartTime(), range.getEndTime());
      default -> throw new IllegalArgumentException(
          "Oops, incorrect transaction type: " + transactionType);
    };
    System.out.println(history);
    System.out.println();
  }

  private static String getTransactionType(boolean isDeposit, boolean isPayment) {
    String transactionType;
    if (isPayment) {
      if (isDeposit) {
        transactionType = "buy-payments";
      } else {
        transactionType = "sell-payments";
      }
    } else {
      if (isDeposit) {
        transactionType = "deposits";
      } else {
        transactionType = "withdrawals";
      }
    }
    return transactionType;
  }
}
