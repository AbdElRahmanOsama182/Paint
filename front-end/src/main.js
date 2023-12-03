import { createApp } from 'vue'
import App from './App.vue'

<<<<<<< HEAD
// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
  components,
  directives,
})

createApp(App).use(vuetify).mount('#app')
=======
createApp(App).mount('#app')
>>>>>>> 024df6a589446c6f2d698b242efd404678f946cb
