package org.d3if3051.assessment2.navigation

import org.d3if3051.assessment2.screen.KEY_ID_FINANCE

sealed class ScreenApp(val route: String) {
   data object Home: ScreenApp("firstScreen")
   data object Content: ScreenApp("appScreen")
   data object NewForm: ScreenApp("addNoteScreen")
   data object ChangeForm: ScreenApp("addNoteScreen/{$KEY_ID_FINANCE}"){
      fun withId(id: Long) = "addNoteScreen/$id"
   }
}