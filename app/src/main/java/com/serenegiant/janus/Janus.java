package com.serenegiant.janus;

import com.serenegiant.janus.request.Attach;
import com.serenegiant.janus.request.Creator;
import com.serenegiant.janus.request.Destroy;
import com.serenegiant.janus.response.Event;
import com.serenegiant.janus.response.ServerInfo;
import com.serenegiant.janus.response.Plugin;
import com.serenegiant.janus.response.Session;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Janus {
	@POST("/")
	public Call<Session> create(@Body final Creator create);

	@GET("/info")
	public Call<ServerInfo> getInfo();

	@GET("/{session_id}")
	public Call<Event> getLongPoll(@Path("session_id") final String sessionId);
	
	@POST("/{session_id}")
	public Call<Plugin> attach(@Body final Attach attach);
	
	@POST()
	public Call<Void> destroy(@Body final Destroy destroy);
}
