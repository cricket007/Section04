class LoginController < ApplicationController
	layout 'login'
	def index
	@test = params[:userId]
	end
end
