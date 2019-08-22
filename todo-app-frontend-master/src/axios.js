import axios from 'axios'

const instance = axios.create({
  // @TODO custom api url here
  baseURL: 'http://localhost:8080'
})

export default instance
