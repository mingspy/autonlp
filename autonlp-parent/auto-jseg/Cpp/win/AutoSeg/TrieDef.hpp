/*
 * Copyright (C) 2014  mingspy@163.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
#pragma once

#include <iostream>
#include <string>
#include <cassert>
#include "FileUtils.hpp"
#include "MemoryPool.hpp"
#include "MemLeaksCheck.h"

using namespace std;

namespace mingspy
{

//typedef wchar_t TrieChar;

const int TRIE_INDEX_ERROR = 0;
const void * TRIE_DATA_ERROR = NULL;
const int TRIE_CHILD_MAX = 65536; // how many children a char can have in dat.

/**
* The length of a TrieStr

int TrieStrLen(const TrieChar * str)
{
    if(str != NULL)
    {
        const TrieChar * p = str;
        int len = 0;
        while(*p++ != TrieChar(0))
        {
            len ++;
        }
        return len;
    }
    return 0;
}
*/

/**
* Tail data delete call back functions
*/
typedef void (*TailDataFreer)(void * );

inline void TrieStrFreer(void * ptr)
{
    wchar_t * p = (wchar_t *) ptr;
    delete [] p;
}


/**
* Write data to a given file, which used for tail to serialize.
*/
typedef void (*TailDataWriter)(FILE * file, const void * data);

/*
* Reads data from given file, which used for tail unserialize.
* if the pmem != null, must allocate att the needed datas on pmem.
* And the allocate data will be release by pmem at once.
* MemoryPool only safe when used to save simple objects that not
* holds other objects which need to free.
*/
typedef void *(*TailDataReader)(FILE * file);

void TrieStrWriter(FILE * file, const void * str)
{
    if(str != NULL) {
        wstring wstr = (wchar_t *)str;
        int len = wstr.length() + 1;
        assert(len < 0xffff);
        if(!file_write_int16(file, len)) {
            assert(false);
        }
        int * buf = new int[len];
        wcharToIntArray(wstr.c_str(), len - 1, buf, len);
        if(fwrite(buf, sizeof(int), len, file) != len) {
            assert(false);
        }
        delete [] buf;
    } else {
        assert(false);
    }
}

/*
* read wstring from file, and return 
* a wchar_t * allocated in heap, caller
* need delete the result when not use.
*/
void * TrieStrReader(FILE * file)
{
    unsigned short len;
    if(!file_read_int16(file, (short *)&len)) {
        assert(false);
        return NULL;
    }
    int *buf = new int[len];
    wchar_t * str = new wchar_t[len];
    

    if(fread(buf, sizeof(int), len, file) != len) {
        assert(false);
        delete [] buf;
        delete [] str;
        return NULL;
    }

    for(int i = 0; i < len; i++){
        str[i] = (wchar_t)buf[i];
    }
    delete [] buf;
    return str;
}
}

