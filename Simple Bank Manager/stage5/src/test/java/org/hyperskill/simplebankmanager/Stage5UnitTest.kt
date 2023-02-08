package org.hyperskill.simplebankmanager

import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.hyperskill.simplebankmanager.internals.screen.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Stage5UnitTest : SimpleBankManagerUnitTest<MainActivity>(MainActivity::class.java) {

    private val DIALOG_BILL_TITLE_SUCCESS: String = "Bill info"
    private val DIALOG_BILL_TITLE_ERROR: String = "Error"
    private val DIALOG_BILL_MESSAGE_ELECTRICITY: String =
        "Name: Electricity\n billcode: ELEC\n amount: 45.0"
    private val DIALOG_BILL_MESSAGE_WATER: String = "Name: Water\n billcode: WTR\n amount: 25.0"
    private val DIALOG_BILL_MESSAGE_GAS: String = "Name: Gas\n billcode: GAS\n amount: 20.0"
    private val DIALOG_BILL_MESSAGE_ERROR: String = "Wrong code"
    private val BILL_CODE_ELECTRICITY: String = "ELEC"
    private val BILL_CODE_WATER: String = "WTR"
    private val BILL_CODE_GAS: String = "GAS"


    @Test
    fun test00_checkNavigationOnPayBills() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }
            PayBillsScreen(this)
        }
    }

    @Test
    fun test01_checkDialogDataCorrectBillCodeInput() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }

            PayBillsScreen(this).apply {
                confirmInputCode(
                    BILL_CODE_ELECTRICITY,
                    DIALOG_BILL_TITLE_SUCCESS,
                    DIALOG_BILL_MESSAGE_ELECTRICITY
                )
            }

        }
    }

    @Test
    fun test02_checkDialogDataIncorrectBillCode() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }

            PayBillsScreen(this).apply {
                confirmInputCode("phone", DIALOG_BILL_TITLE_ERROR, DIALOG_BILL_MESSAGE_ERROR)
            }

        }
    }

    @Test
    fun test03_payBillSuccess() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()
            }
            ViewBalanceScreen(this).apply {
                assertBalanceAmountDisplay(
                    expectedBalance = "100.00\$",
                    caseDescription = "with default initial balance values"
                )
                activity.clickBackAndRun()
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }

            PayBillsScreen(this).apply {
                confirmInputCode(
                    BILL_CODE_WATER,
                    DIALOG_BILL_TITLE_SUCCESS,
                    DIALOG_BILL_MESSAGE_WATER
                ).also {
                    acceptBillPayment("Water")
                }
            }
            activity.clickBackAndRun()
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()
            }
            ViewBalanceScreen(this).apply {
                assertBalanceAmountDisplay(
                    expectedBalance = "75.00\$",
                    caseDescription = "after payment of bill water"
                )
            }

        }
    }

    @Test
    fun test04_checkDialogDataCorrectBillCodeCancelBillPayment() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }

            PayBillsScreen(this).apply {
                confirmInputCode("phone", DIALOG_BILL_TITLE_ERROR, DIALOG_BILL_MESSAGE_ERROR)
                declineBillPayment(BILL_CODE_GAS)
            }

        }
    }
}






