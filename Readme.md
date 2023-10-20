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

 private fun performAbusiveReferenceCheck() {
        if (isAbusiveReferenceEnabledUseCase() &&
            mView.reference.isNotBlank() &&
            paymentHubScreenViewModel.isReferenceEditable &&
            !paymentHubScreenViewModel.isCompany &&
            !paymentHubScreenViewModel.isInvestmentSafeList &&
            !paymentHubScreenViewModel.isSolicitor &&
            !paymentHubScreenViewModel.isHMRCSafeList
        ) {
            getAbusiveReferenceUseCase(mView.reference)
                .doOnSubscribe { mView.showLoading() }
                .subscribe({ abusivePaymentsResponse ->
                    if (abusivePaymentsResponse.valid) {
                        mView.setReferenceToNoneState()
                        initiatePayment()
                    } else {
                        with(mView) {
                            hideLoading()
                            showErrorMessage(resourcesWrapper.getString(R.string.payment_hub_abusive_reference_warning_text))
                            setReferenceToErrorState()
                            setReferenceInputFieldTipViewText(resourcesWrapper.getString(R.string.payment_hub_abusive_reference_hint_text))
                            paymentHubAnalytics.trackAbusiveReferencePopup(mView.reference)
                        }
                    }
                }, {
                    initiatePayment()
                })
        } else {
            initiatePayment()
        }
    }
