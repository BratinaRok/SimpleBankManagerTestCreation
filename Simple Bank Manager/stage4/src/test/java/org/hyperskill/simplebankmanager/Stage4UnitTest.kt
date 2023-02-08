package org.hyperskill.simplebankmanager

import android.content.Intent
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.hyperskill.simplebankmanager.internals.screen.CalculateExchangeScreen
import org.hyperskill.simplebankmanager.internals.screen.LoginScreen
import org.hyperskill.simplebankmanager.internals.screen.UserMenuScreen
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Stage4UnitTest : SimpleBankManagerUnitTest<MainActivity>(MainActivity::class.java) {


    @Test
    fun test00_checkNavigationOnCalculateExchange() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }

            CalculateExchangeScreen(this)
        }
    }


    @Test
    fun test01_convertEURtoGBP() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                assertDisplayConvertedAmount(
                    5067.0,
                    "eur",
                    "gbp",
                    4408.29
                ) // conversion is set to 2 decimal points
            }
        }
    }

    @Test
    fun test02_convertUSDtoEUR() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                assertDisplayConvertedAmount(
                    3424.0,
                    "usd",
                    "eur",
                    3424.00
                ) // conversion is set to 2 decimal points
            }
        }
    }

    @Test
    fun test03_convertGBPtoEUR() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                assertDisplayConvertedAmount(
                    345.0,
                    "gbp",
                    "eur",
                    393.30
                ) // conversion is set to 2 decimal points
            }
        }
    }

    @Test
    fun test04_CheckForErrorSameCurrenciesSelected() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                checkForErrorMessages(isEmptyAmount = true)
            }
        }
    }

    @Test
    fun test05_CheckForErrorEmptyAmount() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                checkForErrorMessages(isSameCurrencySelected = true)
            }
        }
    }

    @Test
    fun test06_CheckIfSameCurrencyCanBeSelected() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                setSpinnerCurrencySelection("USD", "USD")
                val convertFrom = calculateExchangeConvertFromSpinner.selectedItem
                val convertTo = calculateExchangeConvertToSpinner.selectedItem
                    Assert.assertNotEquals("Currencies for" + "\"convert from\"" + " and " +"\"convert to\""
                    + "should not be selected as equal"
                        ,convertFrom,convertTo)
            }
        }
    }

    @Test
    fun test07_convertCustomMap() {

        val exchangeMap: Map<String, Map<String, Double>> = mapOf(
            "EUR" to mapOf(
                "GBP" to 0.5,
                "USD" to 2.0
            ),
            "GBP" to mapOf(
                "EUR" to 2.0,
                "USD" to 4.0
            ),
            "USD" to mapOf(
                "EUR" to 0.5,
                "GBP" to 0.25
            )
        )

        val args = Intent().apply {
            putExtra("exchangeMap", exchangeMap as java.io.Serializable)
        }

        testActivity(arguments = args) {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }

            CalculateExchangeScreen(this).apply {
                for (from in exchangeMap.keys) {
                    val fromMap = exchangeMap[from]!!
                    for (to in fromMap.keys) {
                        val rate = fromMap[to]!!
                        val amountToConvert = 100.0
                        val expectedConvertedAmount = amountToConvert * rate

                        assertDisplayConvertedAmount(
                            amountToConvert,
                            from,
                            to,
                            expectedConvertedAmount
                        ) // conversion is set to 2 decimal points
                    }
                }
            }
        }
    }
}






