package com.plaintext.adapters.dropbox

import com.dropbox.client2.DropboxAPI
import com.dropbox.client2.session._

class DropboxFile(val mDBApi: DropboxAPI[_], val entry: DropboxAPI.Entry) 
{
	def delete() = {
		mDBApi.delete(entry.path)
	}
}