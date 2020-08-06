import ApiService from '@/api'

const PathService = {
  get(params) {
    return ApiService.getWithParams('/paths', params);
  }
}

export default PathService
