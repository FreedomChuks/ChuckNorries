![Group 2](https://user-images.githubusercontent.com/31355965/174633092-cb44cab9-1737-4fc8-adc2-7d9d0c9a9082.png)

## Built With 🛠
- [Kotlin]
- [Coroutines]
- [Android Architecture Components]
  - [Stateflow]
  - [Flow]
  - [ViewModel]
  - [Room]
  - [Jetpack Navigation]
- [Material 3 Components for Android] 
-  [KTLINT for lint checks ]


<br />

## Things i could have done better 📦
 * Seperated each action to a usecase and test in isolation
 * write UI tests
 * Better UI
 * Modularized the Application
    


## Architecture 🗼
This app uses [***MVVM (Model View View-Model)***](
[Unidirectional flow pattern]


## Build-tool 🧰
You need to have [Android Studio Beta 3 or above] 



public class PaymentSummaryViewModelFactory {

    private final ArrangementUseCase mArrangementUseCase;
    private final ResourcesWrapper mResourcesWrapper;
    private final Map<TransactionType, IPaymentSummaryViewModelFactory> mViewModelMap;

    @Inject
    public PaymentSummaryViewModelFactory(ArrangementUseCase arrangementUseCase,
    ResourcesWrapper resourcesWrapper) {
        mArrangementUseCase = arrangementUseCase;
        mResourcesWrapper = resourcesWrapper;
        mViewModelMap = getViewModelMap();
    }

    private interface IPaymentSummaryViewModelFactory {
        PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
        @NonNull TransactionAccounts transactionAccounts);
    }

    @NonNull
    public PaymentHubSummaryViewModel getViewModelForTransactionAccounts(
    @NonNull Transaction transaction,
    @NonNull TransactionAccounts transactionAccounts) {
        final IPaymentSummaryViewModelFactory factory = mViewModelMap.get(transaction.getTransactionType());
        if (factory == null) {
            throw new IllegalStateException("Unknown State");
        }
        return factory.getViewModel(transaction, transactionAccounts);
    }

    @NonNull
    private Map<TransactionType, IPaymentSummaryViewModelFactory> getViewModelMap() {
        final Map<TransactionType, IPaymentSummaryViewModelFactory> handlerMap = new HashMap<>();
        handlerMap.put(TransactionType.TRANSFER, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                return initPaymentHubSummaryTransferArrangementViewModel(
                    (TransferTransaction) transaction, transactionAccounts);
            }
        });
        handlerMap.put(TransactionType.ISA_TRANSFER, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                return initPaymentHubSummaryTransferArrangementViewModel(
                    (IsaTransaction) transaction, transactionAccounts);
            }
        });
        handlerMap.put(TransactionType.CREDIT_CARD_PAYMENT, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                final CreditCardPaymentTransaction cardTransaction = (CreditCardPaymentTransaction) transaction;
                return initPaymentHubSummaryCreditCardArrangementViewModel(cardTransaction,
                    transactionAccounts, isFutureDated(cardTransaction.getTransactionDate()));
            }
        });
        handlerMap.put(TransactionType.PAYMENT, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                final PaymentTransaction paymentTransaction = (PaymentTransaction) transaction;
                return initPaymentHubSummaryBeneficiaryViewModel(paymentTransaction, transactionAccounts,
                    isFutureDated(paymentTransaction.getTransactionDate()));
            }
        });
        handlerMap.put(TransactionType.PAYM, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                return initPaymentHubSummaryMobileNumberViewModel((PayMTransaction) transaction, transactionAccounts);
            }
        });
        handlerMap.put(TransactionType.STANDING_ORDER, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                return initPaymentHubSummaryStandingOrderViewModel(
                    (StandingOrderTransaction) transaction, transactionAccounts);
            }
        });
        handlerMap.put(TransactionType.MORTGAGE_OVERPAYMENT, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                final MortgageTransaction mortgageTransaction = (MortgageTransaction) transaction;
                return initPaymentHubMortgageViewModel(mortgageTransaction, transactionAccounts,
                    isFutureDated(mortgageTransaction.getTransactionDate()));
            }
        });
        handlerMap.put(TransactionType.AMEND_STANDING_ORDER, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                return initPaymentHubSummaryAmendStandingOrderViewModel(
                    (AmendStandingOrderTransaction) transaction, transactionAccounts);
            }
        });
        handlerMap.put(TransactionType.NEW_BENEFICIARY_PAYMENT, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                final PaymentTransaction paymentNewTransaction = (PaymentTransaction) transaction;
                return initPaymentHubSummaryPayNewBeneficiaryViewModel(paymentNewTransaction, transactionAccounts,
                    isFutureDated(paymentNewTransaction.getTransactionDate()));
            }
        });
        handlerMap.put(TransactionType.NEW_BENEFICIARY_STANDING_ORDER, new IPaymentSummaryViewModelFactory() {
            @Override
            public PaymentHubSummaryViewModel getViewModel(@NonNull Transaction transaction,
                @NonNull TransactionAccounts transactionAccounts) {
                return initPaymentHubSummaryPayNewBeneficiarySOViewModel(
                    (StandingOrderTransaction) transaction, transactionAccounts);
            }
        });
        return Collections.unmodifiableMap(handlerMap);
    }

    @NonNull
    private PaymentHubSummaryArrangementViewModel initPaymentHubSummaryTransferArrangementViewModel(
    @NonNull TransferTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts) {
        return new PaymentHubSummaryArrangementViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        PaymentHubArrangementTileViewModel.Companion.createToArrangementTileViewModel(transactionAccounts,
            mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        getFormattedAmount(transaction.getPaymentAmount()), transaction.getReference(), null);
    }

    @NonNull
    private PaymentHubSummaryArrangementViewModel initPaymentHubSummaryTransferArrangementViewModel(
    @NonNull IsaTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts) {
        return new PaymentHubSummaryArrangementViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        PaymentHubArrangementTileViewModel.Companion.createToArrangementTileViewModel(transactionAccounts,
            mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        getFormattedAmount(transaction.getPaymentAmount()), null, null);
    }

    @NonNull
    private PaymentHubSummaryArrangementViewModel initPaymentHubSummaryCreditCardArrangementViewModel(
    @NonNull CreditCardPaymentTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts,
    final boolean isFutureDatePayment) {
        final LocalDate paymentDate = isFutureDatePayment ? transaction.getTransactionDate() : null;
        return new PaymentHubSummaryArrangementViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        PaymentHubArrangementTileViewModel.Companion.createToArrangementTileViewModel(transactionAccounts,
            mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        getFormattedAmount(transaction.getPaymentAmount()), transaction.getReference(), paymentDate);
    }

    @NonNull
    private PaymentHubSummaryBeneficiaryViewModel initPaymentHubSummaryBeneficiaryViewModel(
    @NonNull PaymentTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts,
    final boolean isFutureDatePayment) {
        final LocalDate paymentDate = isFutureDatePayment ? transaction.getTransactionDate() : null;
        final BeneficiaryViewModel beneficiaryViewModel =
            new BeneficiaryViewModel.Builder(transaction)
                .setBeneficiaryType(BeneficiaryType.UK_BANK)
                .setHasUnIndentInfoMargin(true)
                .build();
        return new PaymentHubSummaryBeneficiaryViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        beneficiaryViewModel, PaymentHubViewType.TO,
        getFormattedAmount(transaction.getPaymentAmount()),
        transaction.getReference(), paymentDate);
    }

    @NonNull
    private PaymentHubSummaryMobileNumberViewModel initPaymentHubSummaryMobileNumberViewModel(
    @NonNull PayMTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts) {
        return new PaymentHubSummaryMobileNumberViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        PaymentHubReviewMobileNumberViewModel.Companion.createToMobileNumberTileViewModel(transaction),
        getFormattedAmount(transaction.getPaymentAmount()), transaction.getReference());
    }

    @NonNull
    private PaymentHubSummaryStandingOrderViewModel initPaymentHubSummaryStandingOrderViewModel(
    @NonNull StandingOrderTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts) {
        final PaymentHubReviewToTileViewModel paymentHubReviewToTileViewModel;
        final BeneficiaryViewModel beneficiaryViewModel;
        if (transaction.getRecipientType() == TransactionRecipientType.ARRANGEMENT) {
            paymentHubReviewToTileViewModel =
                PaymentHubArrangementTileViewModel.Companion.createToArrangementTileViewModel(transactionAccounts,
                    mArrangementUseCase.isHCCEnabled(), mResourcesWrapper);
            beneficiaryViewModel = null;
        } else if (transaction.getBeneficiaryType() == BeneficiaryType.UK_BANK) {
            beneficiaryViewModel = new BeneficiaryViewModel.Builder(AssertionUtils
                .nonNull(transactionAccounts.getBeneficiary()))
                .setHasUnIndentInfoMargin(true)
                .build();
            paymentHubReviewToTileViewModel = null;
        } else {
            throw new IllegalStateException("Unsupported recipient type!");
        }
        return new PaymentHubSummaryStandingOrderViewModel.Builder(
            getFormattedAmount(transaction.getPaymentAmount()),
        AssertionUtils.nonNull(transaction.getReference(), "transaction.paymentReference"))
        .setPaymentHubArrangementTileViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper))
            .setBeneficiaryViewModel(beneficiaryViewModel)
            .setViewType(PaymentHubViewType.TO)
            .setPaymentHubReviewToTileViewModel(paymentHubReviewToTileViewModel)
            .setStandingOrderFrequencyCode(transaction.getPaymentFrequency().getFrequencyCode())
            .setStandingOrderFrequency(transaction.getPaymentFrequency().getFrequencyTitle(mResourcesWrapper))
            .setFirstPaymentDate(transaction.getFirstPaymentDate())
            .setLastStatementDate(transaction.getLastPaymentDate())
            .build();
    }

    @NonNull
    private PaymentHubSummaryPayNewBeneficiaryViewModel initPaymentHubSummaryPayNewBeneficiaryViewModel(
    @NonNull PaymentTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts,
    final boolean isFutureDatedPayment) {
        final BeneficiaryViewModel beneficiaryViewModel = new BeneficiaryViewModel.Builder(transaction)
            .setBeneficiaryType(BeneficiaryType.UK_BANK)
            .setHasUnIndentInfoMargin(true)
            .build();
        final LocalDate paymentDate = isFutureDatedPayment ? transaction.getTransactionDate() : null;
        return new PaymentHubSummaryPayNewBeneficiaryViewModel.Builder(
            getFormattedAmount(transaction.getPaymentAmount()), transaction.getReference(), paymentDate)
        .setPaymentHubArrangementTileViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper))
            .setBeneficiaryViewModel(beneficiaryViewModel)
            .setPaymentHubViewType(PaymentHubViewType.TO)
            .build();
    }

    @NonNull
    private PaymentHubSummaryPayNewBeneficiaryViewModel initPaymentHubSummaryPayNewBeneficiarySOViewModel(
    @NonNull StandingOrderTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts) {
        final BeneficiaryViewModel beneficiaryViewModel = new BeneficiaryViewModel.Builder(transaction)
            .setBeneficiaryType(BeneficiaryType.UK_BANK)
            .setHasUnIndentInfoMargin(true)
            .build();
        return new PaymentHubSummaryPayNewBeneficiaryViewModel.Builder(
            getFormattedAmount(transaction.getPaymentAmount()),
        transaction.getReference(), null)
        .setPaymentHubArrangementTileViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper))
            .setBeneficiaryViewModel(beneficiaryViewModel)
            .setPaymentHubViewType(PaymentHubViewType.TO)
            .setStandingOrderFrequencyCode(transaction.getPaymentFrequency().getFrequencyCode())
            .setStandingOrderFrequency(transaction.getPaymentFrequency().getFrequencyTitle(mResourcesWrapper))
            .setFirstPaymentDate(transaction.getFirstPaymentDate())
            .setLastStatementDate(transaction.getLastPaymentDate())
            .setStandingOrder(true)
            .build();
    }

    private PaymentHubMortgageViewModel initPaymentHubMortgageViewModel(
    @NonNull MortgageTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts,
    final boolean isFutureDatePayment) {
        final LocalDate paymentDate = isFutureDatePayment ? transaction.getTransactionDate() : null;
        return new PaymentHubMortgageViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        PaymentHubArrangementTileViewModel.Companion.createToArrangementTileViewModel(transactionAccounts,
            mArrangementUseCase.isHCCEnabled(), mResourcesWrapper),
        getFormattedAmount(transaction.getPaymentAmount()), transaction.getReference(), paymentDate,
        transaction.getSubAccountId());
    }

    private static String getFormattedAmount(String amount) {
        return StringFormatUtils.formatAmountString(StringFormatUtils
            .formatDoubleWithoutGrouping(StringFormatUtils
                .parseAmount(amount, CurrencyCode.getDefaultCurrencyCode())));
    }

    private PaymentHubSummaryStandingOrderViewModel initPaymentHubSummaryAmendStandingOrderViewModel(
    @NonNull AmendStandingOrderTransaction transaction,
    @NonNull TransactionAccounts transactionAccounts) {
        final BeneficiaryViewModel beneficiaryViewModel = new BeneficiaryViewModel.Builder(transaction)
            .setBeneficiaryType(BeneficiaryType.UK_BANK)
            .setHasUnIndentInfoMargin(true)
            .build();
        return new PaymentHubSummaryStandingOrderViewModel.Builder(
            getFormattedAmount(transaction.getPaymentAmount()),
        StringUtils.emptyIfNull(transaction.getReference()))
        .setPaymentHubArrangementTileViewModel(
            PaymentHubArrangementTileViewModel.Companion.createFromArrangementTileViewModel(transactionAccounts,
                mArrangementUseCase.isHCCEnabled(), mResourcesWrapper))
            .setBeneficiaryViewModel(beneficiaryViewModel)
            .setViewType(PaymentHubViewType.TO)
            .setStandingOrderFrequencyCode(transaction.getPaymentFrequency().getFrequencyCode())
            .setStandingOrderFrequency(transaction.getPaymentFrequency().getFrequencyTitle(mResourcesWrapper))
            .setFirstPaymentDate(transaction.getFirstPaymentDate())
            .setLastStatementDate(transaction.getLastPaymentDate())
            .build();
    }

    private boolean isFutureDated(@Nullable LocalDate transactionDate) {
        return transactionDate != null && DateTimeUtils.isDateInTheFuture(transactionDate);
    }
}
