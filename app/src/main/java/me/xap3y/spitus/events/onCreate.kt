@file:Suppress("ClassName")

package me.xap3y.spitus.events

import android.content.Context
import android.view.View
import me.xap3y.spitus.Utils.DataManager
import me.xap3y.spitus.Utils.Logger
import me.xap3y.spitus.Utils.MaterialD
import me.xap3y.spitus.Utils.SafeCallBack
import me.xap3y.spitus.Utils.StorageManager
import me.xap3y.spitus.Utils.strucs.CallBackResult
class onCreate {

    companion object{
        private lateinit var callRes: CallBackResult
        fun firstTimeLaunch(dataManager: DataManager) : Boolean {
            callRes = SafeCallBack.Callback { dataManager.getBool("env_firstTimeLaunch", true) }

            if(callRes.success && (callRes.result != null) && (callRes.result as Boolean)) {

                Logger.logger(
                    Logger.DEBUG,
                    "DataManager",
                    "Save call finished in ${callRes.runTime}ms with result ${callRes.result}"
                )

                Logger.logger(
                    Logger.DEBUG,
                    "DataManager",
                    "App launched for first time, creating default servers JSON."
                )
                return try {
                    var _temp = SafeCallBack.Callback { dataManager.saveString("json", StorageManager.createDefaultJsonString()) }
                    if (!_temp.success) {
                        Logger.logger(
                            Logger.ERROR,
                            "DataManager",
                            "Cannot create default servers JSON! ERR: ${_temp.errorMessage}",
                        )
                        return false;
                    }

                    _temp = SafeCallBack.Callback { dataManager.setBool("env_firstTimeLaunch", false) }
                    if (!_temp.success) {
                        Logger.logger(
                            Logger.ERROR,
                            "DataManager",
                            "Cannot set env_firstTimeLaunch to false! ERR: ${_temp.errorMessage}",
                        )

                        TODO("Un-Comment after first stable release")
                        //return false;
                    }
                    Logger.logger(Logger.DEBUG, "DataManager", "Default servers JSON saved.")
                    true
                } catch (e: Exception) {
                    Logger.logger(
                        Logger.ERROR,
                        "DataManager",
                        "Cannot create default servers JSON!"
                    )
                    false
                }
            } else {
                return true;
            }
        }
    }
}