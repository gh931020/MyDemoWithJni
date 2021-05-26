package com.example.mydemowithjni.room

import android.graphics.Bitmap
import androidx.room.*
import java.util.*

/**
 * 默认情况下,Room将类形成作为数据库表名称.
 * 可通过tableName自定义表名[表名不区分大小写]
 * @property uid Int
 * @property firstName String
 * @property lastName String
 * @constructor
 */
@Entity(primaryKeys = ["uid", "firstName"], tableName = "myuser")
data class User(
    /**
     * 每隔实体必须将至少1个字段定义为主键, 即使只有1个字段,您仍然需要添加@PrimaryKey注释.
     * 如果想让Room为实体分配自动ID, 可以设置autoGenerate属性
     * 如果具有复合主键,可以使用@Entity注释的primaryKeys属性
     */
    @PrimaryKey(autoGenerate = true)
    var uid: Long,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String,
    @ColumnInfo(name = "age")
    var age: Int,
    // 使用@Ignore忽略该属性,,如果要忽略继承的父类的属性,使用@Entity(ignoredColumns = arrayof("name"))
    @Ignore
    var avtar: Bitmap,
    // 表示 User 对象的表将包含具有以下名称的列：id、firstName、street、state、city 和 post_code。
    // 如果某个实体具有相同类型的多个嵌套字段，您可以通过设置 prefix 属性确保每个列的唯一性。
    @Embedded(prefix = "address")
    var address: Address?,
    val birthday: Date?
)

data class Address(
    val street: String?,
    val state: String?,
    val city: String?,
    @ColumnInfo(name = "post_code")
    val postCode: Int
)

@Entity
data class Library(
    @PrimaryKey
    val libraryId: Long,
    val userOwnerId: Long
)

/**
 * 一对一
 * @property user User
 * @property library Library
 * @constructor
 */
data class UserAndLibrary(
    @Embedded
    val user: User,
    @Relation(parentColumn = "uid", entityColumn = "userOwnerId")
    val library: Library
)

@Entity
data class PlayList(
    @PrimaryKey
    val playlistId: Long,
    val userCreatorId: Long,
    val playlistName: String
)

/**
 * 一对多
 * @property user User
 * @property playlists List<PlayList>
 * @constructor
 */
data class UserAndPlaylists(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "uid",
        entityColumn = "userCreatorId"
    )
    val playlists: List<PlayList>
)

@Entity
data class Song(
    @PrimaryKey
    val songId: Long,
    val songName: String,
    val artist: String
)

/**
 * 首先，为您的两个实体分别创建一个类。
 * 多对多关系与其他关系类型均不同的一点在于，子实体中通常不存在对父实体的引用。
 * 因此，需要创建第三个类来表示两个实体之间的关联实体（即交叉引用表）。
 * 交叉引用表中必须包含表中表示的多对多关系中每个实体的主键列。
 * 在本例中，交叉引用表中的每一行都对应于 Playlist 实例和 Song 实例的配对，其中引用的歌曲包含在引用的播放列表中。
 * @property playlistId Long
 * @property songId Long
 * @constructor
 */
@Entity(primaryKeys = ["playlistId", "songId"])
data class PlaylistSongCrossRef(
    val playlistId: Long,
    val songId: Long
)

/**
 * 查询播放列表和每个播放列表所含歌曲的列表，则应创建一个新的数据类，
 * 其中包含单个 Playlist 对象，以及该播放列表所包含的所有 Song 对象的列表。
 * @property playlist PlayList
 * @property songs List<Song>
 * @constructor
 */
data class PlaylistWithSongs(
    @Embedded
    val playlist: PlayList,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "songId",
        associateBy = Junction(value = PlaylistSongCrossRef::class)
    )
    val songs: List<Song>
)

/**
 * 查询歌曲和每首歌曲所在播放列表的列表，则应创建一个新的数据类，其中包含单个 Song 对象，
 * 以及包含该歌曲的所有 Playlist 对象的列表。
 *
 * 在上述每个类中的 @Relation 注释中使用 associateBy 属性来确定提供 Playlist 实体与 Song 实体之间关系的交叉引用实体。
 * @property song Song
 * @property playlists List<PlayList>
 * @constructor
 */
data class SongWithPlaylists(
    @Embedded
    val song: Song,
    @Relation(
        parentColumn = "songId",
        entityColumn = "playlistId",
        associateBy = Junction(PlaylistSongCrossRef::class)
    )
    val playlists: List<PlayList>
)

/**
 * 创建另一个数据类，用于在集合中的另一个表与第一个关系类之间建立关系，并将现有关系嵌套到新关系中。
 * 以下示例展示了一个 UserWithPlaylistsAndSongs 类，该类可在 User 实体类和 PlaylistWithSongs 关系类之间建立一对多关系
 * @property user User
 * @property playlists List<PlaylistWithSongs>
 * @constructor
 */
data class UserWithPlaylistsAndSongs(
    @Embedded
    val user: User,
    @Relation(
        entity = PlayList::class,
        parentColumn = "uid",
        entityColumn = "userCreatorId"
    )
    val playlists: List<PlaylistWithSongs>
)

