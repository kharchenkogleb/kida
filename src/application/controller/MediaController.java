package application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import application.Playlist;
import application.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class MediaController {
	
	private static final MediaController mediaController = new MediaController();

	private PlayerController playerController = PlayerController.getInstance();
	
	private Playlist allSongs = new Playlist("All Songs");
	private Playlist favorites = new Playlist("Favoriten");
	private ObservableList<Playlist> myPlaylists = FXCollections.observableArrayList();
	private ObservableList<Playlist> artistPlaylists = FXCollections.observableArrayList();
	private ObservableList<Playlist> albumPlaylists = FXCollections.observableArrayList();
	private ObservableList<Playlist> genrePlaylists = FXCollections.observableArrayList();
	private Playlist selectedPlaylist;
	private File allSongsFile = new File("./saves/allSongsFile.txt");
	private File myPlaylistsFile = new File("./saves/myPlaylistsFile.txt");
	private File currentSongFile = new File("./saves/currentSongFile.txt");
	private File currentPlaylistFile = new File("./saves/currentPlaylistFile.txt");
	
	public static MediaController getInstance() {
		return mediaController;
	}
	
	public MediaController() {
		readAllFromFile();
	}
	
	public void addToAllSongs(Song song) {
		allSongs.getSongs().add(song);
	}
	
	public void addToAllSongsAndSave(Song song) {
		allSongs.getSongs().add(song);
		saveAllToFile();
	}
	
	public void deleteFromAllSongs(Song song) {
		allSongs.getSongs().remove(song);
		saveAllToFile();
	}

	public Playlist getAllSongs() {
		return allSongs;
	}	

	public ObservableList<Playlist> getAllPlaylists() {
		return myPlaylists;
	}

	public void setAllPlaylists(ObservableList<Playlist> allPlaylists) {
		this.myPlaylists = allPlaylists;
		saveAllToFile();
	}
	
	public Playlist getPlaylistByName(String name) {
		return myPlaylists.stream().filter(element-> element.getName().equals(name)).findFirst().orElse(null);
	}
	
	public Playlist getSelectedPlaylist() {
		return selectedPlaylist;
	}

	public void setSelectedPlaylist(Playlist selectedPlaylist) {
		this.selectedPlaylist = selectedPlaylist;
		saveAllToFile();
	}
	
	public void createPlaylist(String name) {
		myPlaylists.add(new Playlist(name));
		saveAllToFile();
	}
	
	public void deletePlaylist(Playlist playlist) {
		myPlaylists.remove(playlist);
		saveAllToFile();
	}
	
	public void deletePlaylistByName(String name) {
		myPlaylists.remove(getPlaylistByName(name));
		saveAllToFile();
	}


	public void addSong(Song song, Playlist playlist) {
		playlist.getSongs().add(song);
		saveAllToFile();
	}
	
	public void removeSong(Song song, Playlist playlist) {
		playlist.getSongs().remove(song);
		saveAllToFile();
	}
	
	public void setOnFavorites(Song s) {
		s.setLike(true);
	}
	
	public void deleteOnFavorites(Song s) {
		s.setLike(false);
	}
	
	public Playlist getFavorites() {
		favorites.setSongs(FXCollections.observableArrayList(allSongs.getSongs().stream().filter(element -> element.isLike()).collect(Collectors.toList())));
		return favorites;
	}
	
	public ObservableList<Song> search(String input, String type) {
		Stream<Song> result;
		String word = input.toLowerCase();
		switch (type) {
		case "Titel":
			result = allSongs.getSongs().stream().filter(item -> item.getTitle().toLowerCase().contains(word));	
			break;
		case "Interpret":
			result = allSongs.getSongs().stream().filter(item -> item.getArtist().toLowerCase().contains(word));	     	
			break;
		case "Album":
			result = allSongs.getSongs().stream().filter(item -> item.getAlbum().toLowerCase().contains(word));     
			break;
		case "Genre":
			result = allSongs.getSongs().stream().filter(item -> item.getGenre().toLowerCase().contains(word));
			break;
		default:
			result = allSongs.getSongs().stream().filter(item -> item.getTitle().toLowerCase().contains(word));	     
			break;
		}
		return  FXCollections.observableArrayList(result.collect(Collectors.toList()));
	}
	
	public void saveAllToFile() {	
		saveToFile(allSongsFile, new ArrayList<Song>(allSongs.getSongs()));
		Map<String, List<Song>> myPlaylistsToSave = new HashMap<>();
		for (Playlist playlist: myPlaylists) myPlaylistsToSave.put(playlist.getName(), new ArrayList<>(playlist.getSongs()));
		saveToFile(myPlaylistsFile, myPlaylistsToSave);
		saveToFile(currentSongFile, playerController.getCurrentSong());
		//Map<String, List<Song>> currentPlaylistToSave = new HashMap<>();
		//List<Song> tempList = new ArrayList<>();
		//for (Song song : playerController.getCurrentPlaylist().getSongs()) tempList.add(song);
		//currentPlaylistToSave.put(playerController.getCurrentPlaylist().getName(), tempList);
		saveToFile(currentPlaylistFile, playerController.getCurrentPlaylist().getName());
	}

	private void saveToFile(File file, Object object) {
        try {
        	if (!file.exists()) file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            System.out.println("\n(i) Data has been saved to file!");
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("\n(!) " + file.getName() + ": "+ e.getMessage());
        }
    }
	
	public void readAllFromFile() {
		readAllSongsFromFile();
		readMyPlaylistsFromFile();
		readCurrentSongFromFile();
		readCurrentPlaylistFromFile();
		// TODO: Set currentPlaylist to saved playlist in file
		//playerController.setCurrentPlaylist(allSongs);

	}

    @SuppressWarnings("unchecked")
    private void readAllSongsFromFile() {
        try {
            FileInputStream fis = new FileInputStream(allSongsFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            allSongs.setSongs(FXCollections.observableArrayList((ArrayList<Song>) ois.readObject()));
            ois.close(); fis.close();
        } catch (Exception e) {
            System.out.println("\n(!) ALLSONGS: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void readMyPlaylistsFromFile() {
        try {
            FileInputStream fis = new FileInputStream(myPlaylistsFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Map<String, List<Song>> rawInput = ((Map<String, List<Song>>) ois.readObject());
            List<Playlist> input = new ArrayList<>();
            for (String name : rawInput.keySet()) input.add(new Playlist(name, FXCollections.observableArrayList(rawInput.get(name))));
            setAllPlaylists(FXCollections.observableArrayList(input));
            ois.close(); fis.close();
        } catch (Exception e) {
            System.out.println("\n(!) MYPLAYLISTS: " + e.getMessage());
        }
    }
    
    private void readCurrentSongFromFile() {
        try {
            FileInputStream fis = new FileInputStream(currentSongFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            playerController.setCurrentSong((Song) ois.readObject());
            playerController.updateCurrentSong((Song) ois.readObject());
            ois.close(); fis.close();
        } catch (Exception e) {
            System.out.println("\n(!) CURRENTSONG: " + e.getMessage());
        }
    }
    
	private void readCurrentPlaylistFromFile() {
		try {
			FileInputStream fis = new FileInputStream(currentPlaylistFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			////playerController.setCurrentPlaylist((Playlist) ois.readObject());
			//Map<String, List<Song>> rawInput = ((Map<String, List<Song>>) ois.readObject());
			playerController.setCurrentPlaylist(getPlaylistByName((String) ois.readObject()));
			////playerController.setCurrentPlaylist(FXCollections.observableArrayList((ArrayList<Song>) ois.readObject()));
			ois.close(); fis.close();
		} catch (Exception e) {
			System.out.println("\n(!) CURRENTPLAYLIST: " + e.getMessage());
		}
	}

	public ObservableList <Playlist> getArtistPlaylists() {
		return artistPlaylists;
	}
	
	public ObservableList<Playlist> getAlbumPlaylists() {
		return albumPlaylists;
	}

	public ObservableList<Playlist> getGenrePlaylists() {
		return genrePlaylists;
	}

	public void setArtistPlaylists() {
		Set <String> artistList=new HashSet<>(allSongs.getSongs().stream().map(Song::getArtist).collect(Collectors.toList()));
		for(String s:artistList) {
			artistPlaylists.add(new Playlist(s));
		}
		for(Song song:allSongs.getSongs()) {
			artistPlaylists.stream().filter(element-> element.getName().equals(song.getArtist())).findFirst().orElse(null).getSongs().add(song);
		}
	}

	public void setAlbumPlaylists() {
		Set <String> albumList=new HashSet<>(allSongs.getSongs().stream().map(Song::getAlbum).collect(Collectors.toList()));
		for(String s:albumList) {
			albumPlaylists.add(new Playlist(s));
		}
		for(Song song:allSongs.getSongs()) {
			albumPlaylists.stream().filter(element-> element.getName().equals(song.getAlbum())).findFirst().orElse(null).getSongs().add(song);
		}
	}
	
	public void setGenrePlaylists() {
		Set <String> genreList=new HashSet<>(allSongs.getSongs().stream().map(Song::getGenre).collect(Collectors.toList()));
		for(String s:genreList) {
			genrePlaylists.add(new Playlist(s));
		}
		for(Song song:allSongs.getSongs()) {
			genrePlaylists.stream().filter(element-> element.getName().equals(song.getGenre())).findFirst().orElse(null).getSongs().add(song);
		}
	}
	
	public String randomVideoPath() {
		return "gibts noch nicht";
	}
}
