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


    private fun getViewModelMap(): Map<TransactionType, IPaymentSummaryViewModelFactory> {
        val handlerMap: MutableMap<TransactionType, IPaymentSummaryViewModelFactory> = EnumMap(TransactionType::class.java)

        
        handlerMap[TransactionType.TRANSFER] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel =
                initPaymentHubSummaryTransferArrangementViewModel(
                    transaction as TransferTransaction,
                    transactionAccounts
                )
        }


        handlerMap[TransactionType.ISA_TRANSFER] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel =
                initPaymentHubSummaryTransferArrangementViewModel(
                    transaction as IsaTransaction,
                    transactionAccounts
                )
        }

        handlerMap[TransactionType.CREDIT_CARD_PAYMENT] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel {
                val cardTransaction = transaction as CreditCardPaymentTransaction
                return initPaymentHubSummaryCreditCardArrangementViewModel(
                    cardTransaction,
                    transactionAccounts,
                    isFutureDated(cardTransaction.transactionDate)
                )
            }
        }

        handlerMap[TransactionType.PAYMENT] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel {
                val paymentTransaction = transaction as PaymentTransaction
                return initPaymentHubSummaryBeneficiaryViewModel(
                    paymentTransaction,
                    transactionAccounts,
                    isFutureDated(paymentTransaction.transactionDate)
                )
            }
        }

        handlerMap[TransactionType.PAYM] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel = initPaymentHubSummaryMobileNumberViewModel(
                transaction as PayMTransaction,
                transactionAccounts
            )
        }

        handlerMap[TransactionType.STANDING_ORDER] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel =
                initPaymentHubSummaryStandingOrderViewModel(
                    transaction as StandingOrderTransaction,
                    transactionAccounts
                )
        }

        handlerMap[TransactionType.MORTGAGE_OVERPAYMENT] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel {
                val mortgageTransaction = transaction as MortgageTransaction
                return initPaymentHubMortgageViewModel(
                    mortgageTransaction,
                    transactionAccounts,
                    isFutureDated(mortgageTransaction.transactionDate)
                )
            }
        }

        handlerMap[TransactionType.AMEND_STANDING_ORDER] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel =
                initPaymentHubSummaryAmendStandingOrderViewModel(
                    transaction as AmendStandingOrderTransaction,
                    transactionAccounts
                )
        }

        handlerMap[TransactionType.NEW_BENEFICIARY_PAYMENT] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel {
                val paymentNewTransaction = transaction as PaymentTransaction
                return initPaymentHubSummaryPayNewBeneficiaryViewModel(
                    paymentNewTransaction,
                    transactionAccounts,
                    isFutureDated(paymentNewTransaction.transactionDate)
                )
            }
        }

        handlerMap[TransactionType.NEW_BENEFICIARY_STANDING_ORDER] = object : IPaymentSummaryViewModelFactory {
            override fun getViewModel(
                transaction: Transaction,
                transactionAccounts: TransactionAccounts
            ): PaymentHubSummaryViewModel {
                return initPaymentHubSummaryPayNewBeneficiarySOViewModel(
                    transaction as StandingOrderTransaction,
                    transactionAccounts
                )
            }
        }
        return handlerMap.toMap()
    }
